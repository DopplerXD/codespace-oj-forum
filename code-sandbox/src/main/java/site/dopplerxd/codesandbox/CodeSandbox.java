package site.dopplerxd.codesandbox;


import site.dopplerxd.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 11:25
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
