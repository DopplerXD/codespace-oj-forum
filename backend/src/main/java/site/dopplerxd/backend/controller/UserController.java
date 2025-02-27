package site.dopplerxd.backend.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.config.filter.JwtAuthenticationTokenFilter;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.model.dto.user.UserLoginDto;
import site.dopplerxd.backend.model.dto.user.UserRegisterDto;
import site.dopplerxd.backend.model.entity.User;
import site.dopplerxd.backend.model.vo.LoginUserVO;
import site.dopplerxd.backend.service.UserService;
import site.dopplerxd.backend.utils.JwtUtils;

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
    public BaseResponse<String> userRegister(@Validated @RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request) {
        if (userRegisterDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.userRegister(userRegisterDto, request);
        return ResultUtils.success("注册成功，请返回登录！");
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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码为空");
        }
        boolean rememberMe = userLoginDto.isRememberMe();
        Map<String, Object> responseMap = userService.userLogin(username, password, rememberMe, request);
        return ResultUtils.success(responseMap);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    @PostMapping("/logout")
    public BaseResponse<String> userLogout(HttpServletRequest request) {
        userService.userLogout(JwtAuthenticationTokenFilter.getJwtFromRequest(request));
        return ResultUtils.success("注销成功");
    }
}
