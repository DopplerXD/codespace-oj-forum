package site.dopplerxd.codesandbox.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.codesandbox.JavaDockerCodeSandboxImpl;
import site.dopplerxd.codesandbox.JavaNativeCodeSandboxImpl;
import site.dopplerxd.codesandbox.model.ExecuteCodeRequest;
import site.dopplerxd.codesandbox.model.ExecuteCodeResponse;

@RestController("/")
public class MainController {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";


    @Resource
    private JavaNativeCodeSandboxImpl javaNativeCodeSandbox;

    @Resource
    private JavaDockerCodeSandboxImpl javaDockerCodeSandbox;

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest,
                                    HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (authHeader == null || !authHeader.equals(AUTH_REQUEST_SECRET)) {
            response.setStatus(401);
            return null;
        }

        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数异常");
        }
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }
}
