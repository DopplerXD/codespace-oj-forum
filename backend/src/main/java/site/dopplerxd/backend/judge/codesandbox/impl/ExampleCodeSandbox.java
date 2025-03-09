package site.dopplerxd.backend.judge.codesandbox.impl;

import site.dopplerxd.backend.judge.codesandbox.CodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;
import site.dopplerxd.backend.model.enmus.JudgeSubmitStatus;

import java.util.List;

/**
 * 示例代码沙箱
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 14:46
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        List<String> inputList = request.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(JudgeSubmitStatus.STATUS_ACCEPTED.getCode());
        executeCodeResponse.setTime(100L);
        executeCodeResponse.setMemory(199L);
        return executeCodeResponse;
    }
}
