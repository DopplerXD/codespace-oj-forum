package site.dopplerxd.backend.judge.codesandbox;

import lombok.extern.slf4j.Slf4j;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱代理类
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 15:08
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        log.info("代码沙箱请求信息：" + request.toString());
        ExecuteCodeResponse response = codeSandbox.executeCode(request);
        log.info("代码沙箱响应信息：" + response.toString());
        return response;
    }
}
