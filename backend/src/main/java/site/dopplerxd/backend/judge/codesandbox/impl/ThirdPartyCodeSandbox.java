package site.dopplerxd.backend.judge.codesandbox.impl;

import site.dopplerxd.backend.judge.codesandbox.CodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 14:47
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
