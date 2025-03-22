package site.dopplerxd.codesandbox.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.codesandbox.JavaDockerCodeSandboxImpl;
import site.dopplerxd.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.codesandbox.model.ExecuteCodeResponse;

@RestController("/")
public class MainController {

    @Resource
    private JavaDockerCodeSandboxImpl javaDockerCodeSandbox;

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求方式异常");
        }
        return javaDockerCodeSandbox.executeCode(executeCodeRequest);
    }
}
