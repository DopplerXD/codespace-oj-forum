package site.dopplerxd.backend.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.judge.codesandbox.CodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 14:46
 */
public class RemoteCodeSandbox implements CodeSandbox {

    // TODO: 将根据language选择不同语言代码沙箱的任务交给代码沙箱服务端完成
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header("auth", "secretKey")
                .body(json)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
