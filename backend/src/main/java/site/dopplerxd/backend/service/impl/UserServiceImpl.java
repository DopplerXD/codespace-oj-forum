package site.dopplerxd.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.config.filter.JwtAuthenticationTokenFilter;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.mapper.UserMapper;
import site.dopplerxd.backend.model.dto.user.UserRegisterDto;
import site.dopplerxd.backend.model.entity.User;
import site.dopplerxd.backend.model.vo.LoginUserVO;
import site.dopplerxd.backend.service.UserService;
import site.dopplerxd.backend.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author doppleryxc
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-02-20 17:43:59
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String SALT = "valorant";

    private static final String USER_LOGOUT_CACHE_KEY = "codespace:cache:userlogout:";


    @Override
    public void userRegister(UserRegisterDto userRegisterDto, HttpServletRequest request) {
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String checkPassword = userRegisterDto.getCheckPassword();
        // 1. 校验
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度少于4位");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码或确认密码长度少于8位");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (username.intern()) {
            // 账号不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUsername(username);
            user.setPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败");
            }
        }
    }

    @Override
    public Map<String, Object> userLogin(String username, String password, boolean rememberMe, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 创建token
        String token = JwtUtils.generateUserToken(user.getId(), username, user.getRole(), rememberMe);
        // 4. 返回token
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("loginUserVO", getLoginUserVO(user));
        return map;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        String token = JwtAuthenticationTokenFilter.getJwtFromRequest(request);
        if (JwtUtils.verify(token)) {
            // 判断token是否在Redis黑名单中
            String tokenCache = stringRedisTemplate.opsForValue().get(USER_LOGOUT_CACHE_KEY + token);
            if (StrUtil.isNotBlank(tokenCache)) {
                throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户已注销");
            }
            String id = JwtUtils.getUserIdFromRequest(request);
            User user = this.getById(id);
            if (user == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
            }
            return user;
        }
        throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
    }

    /**
     * 获取当前登录用户VO
     *
     * @param user
     * @return
     */
    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 用户注销
     *
     * @param token
     * @return
     */
    @Override
    public void userLogout(String token) {
        // 添加token到Redis黑名单，过期时间为勾选自动登录的token时间
        stringRedisTemplate.opsForValue().set(USER_LOGOUT_CACHE_KEY + token, "1", 7, TimeUnit.DAYS);
    }
}
