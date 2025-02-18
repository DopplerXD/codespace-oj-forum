package site.dopplerxd.backend.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author doppleryxc
 */
@RestController
@Tag(name = "用户接口")
@RequestMapping("/user")
public class TestController {

    @Operation(summary = "用户信息")
    @GetMapping("/info")
    public String test(){
        return "User Info";
    }

    @Operation(summary = "你好")
    @GetMapping("/hello")
    public Object test2(){
        return "hello";
    }
}