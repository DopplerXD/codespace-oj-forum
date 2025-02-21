package site.dopplerxd.backend.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.model.dto.user.UserLoginDto;
import site.dopplerxd.backend.model.dto.user.UserRegisterDto;
import site.dopplerxd.backend.service.UserService;

import java.util.Map;

/**
 * 用户相关接口
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/19 19:22
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request) {
        if (userRegisterDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String checkPassword = userRegisterDto.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String result = userService.userRegister(username, password, checkPassword, request);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginDto
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<Map<String, Object>> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        if (userLoginDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "必要参数为空");
        }
        Map<String, Object> responseMap = userService.userLogin(username, password, request);
        return ResultUtils.success(responseMap);
    }
}
