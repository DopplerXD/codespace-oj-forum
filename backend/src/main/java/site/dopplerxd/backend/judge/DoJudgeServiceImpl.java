package site.dopplerxd.backend.judge;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.judge.codesandbox.CodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.CodeSandboxFactory;
import site.dopplerxd.backend.judge.codesandbox.CodeSandboxProxy;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;
import site.dopplerxd.backend.model.enmus.JudgeSubmitStatus;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.service.ProblemService;

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
        if (judge.getStatus() == 9) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在提交，请稍后");
        }

        // 2）如果题目提交状态不为等待中，就不用重复执行了
        // 3）更改判题（题目提交）的状态为"判题中"，防止重复执行，也能让用户即时看到状态

        Judge judgeUpdate = judge;
        judgeUpdate.setStatus(JudgeSubmitStatus.STATUS_JUDGING.getCode());

        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxType);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = judge.getLanguage();
        String code = judge.getCode();
        String judgeCaseStr = problem.get

        // TODO: 执行判题逻辑
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        return null;
    }
}
