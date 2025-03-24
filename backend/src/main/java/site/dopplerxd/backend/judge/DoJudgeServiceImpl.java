package site.dopplerxd.backend.judge;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.judge.codesandbox.CodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.CodeSandboxFactory;
import site.dopplerxd.backend.judge.codesandbox.CodeSandboxProxy;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;
import site.dopplerxd.backend.model.enmus.JudgeSubmitStatus;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.entity.ProblemCase;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.service.ProblemCaseService;
import site.dopplerxd.backend.service.ProblemService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 15:30
 */
@Service
public class DoJudgeServiceImpl implements DoJudgeService {

    @Resource
    private JudgeService judgeService;

    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemCaseService problemCaseService;

    @Value("${codesandbox.type}")
    private String codeSandboxType;

    /**
     * 执行判题逻辑
     *
     * @param submitId
     * @return
     */
    @Override
    public ExecuteCodeResponse doJudge(long submitId) {
        // 1）传入题目的提交id，获取到对应的题目、提交信息（包含代码、编程浯言等）
        //    如果题目提交信息不存在，就抛出异常
        Judge judge = judgeService.getById(submitId);
        if (judge == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Problem problem = problemService.getById(judge.getPid());
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        if (judge.getStatus() == JudgeSubmitStatus.STATUS_SUBMITTING.getCode()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在提交，请稍后");
        }

        // 2）如果题目提交状态不为等待中，就不用重复执行了
        // 3）更改判题（题目提交）的状态为"判题中"，防止重复执行，也能让用户即时看到状态

        Judge judgeUpdate = judge;
        judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_SUBMITTING.getCode());

        // 4）调用沙箱，执行代码，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxType);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = judge.getLanguage();
        String code = judge.getCode();
        ProblemCase problemCase = problemCaseService.getById(problem.getProblemCaseId());

        // 判题用例格式转换
        String judgeCaseStr = problemCase.getCases();
        JSONArray judgeCaseJsonArray = JSONUtil.parseArray(judgeCaseStr);
        List<String> stdInputList = new ArrayList<>();
        List<String> stdOutputList = new ArrayList<>();

        // 提取 "input" 字段
        judgeCaseJsonArray.forEach(item -> {
            if (item instanceof JSONObject jsonObject) {
                String input = jsonObject.getStr("input");
                String output = jsonObject.getStr("output");
                if (input != null) {
                    stdInputList.add(input);
                }
                if (output!= null) {
                    stdOutputList.add(output);
                }
            }
        });

        // TODO: 执行判题逻辑
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(stdInputList);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage(language);

        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList = executeCodeResponse.getOutputList();
        String message = executeCodeResponse.getMessage();
        Integer status = executeCodeResponse.getStatus();
        Long time = executeCodeResponse.getTime();
        Long memory = executeCodeResponse.getMemory();

        // 对比输出与答案

        judgeUpdate.setScore(0);
        if (status == 2) {
            judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_SYSTEM_ERROR.getCode());
        } else if (status != 1) {
            if (memory > problem.getMemoryLimit()) {
                judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_MEMORY_LIMIT_EXCEEDED.getCode());
            } else if (time > problem.getTimeLimit()) {
                judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_TIME_LIMIT_EXCEEDED.getCode());
            } else {
                judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_RUNTIME_ERROR.getCode());
            }
        } else {
            boolean accepted = checkAnswer(stdOutputList, outputList);
            if (accepted) {
                judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_ACCEPTED.getCode());
                judgeUpdate.setScore(1);
            } else {
                judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_WRONG_ANSWER.getCode());
            }
        }

        judgeUpdate.setErrorMessage(message);
        judgeUpdate.setTime(time);
        judgeUpdate.setMemory(memory);

        ThrowUtils.throwIf(!judgeService.updateById(judgeUpdate), ErrorCode.SYSTEM_ERROR, "数据库更新异常");

        return executeCodeResponse;
    }

    private boolean checkAnswer(List<String> stdOutput, List<String> userOutput) {
        return stdOutput.equals(userOutput);
    }
}
