package site.dopplerxd.backend.utils;

import cn.hutool.jwt.JWT;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.config.filter.JwtAuthenticationTokenFilter;
import site.dopplerxd.backend.exception.BusinessException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/19 10:45
 */
@Component
public class JwtUtils {

    /**
     * JWT有效期7天
     */
    private static final byte[] JWT_SECRET = "valorant".getBytes();
    private static final long JWT_TTL = 1000 * 60 * 60 * 24;

    private JwtUtils() {
    }

    /**
     * 根据登录信息生成Token
     *
     * @param id
     * @param username
     * @param role
     * @return
     */
    public static String generateUserToken(String id, String username, String role, boolean rememberMe) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("role", role);
        return generateToken(map, rememberMe);
    }

    /**
     * 生成Token
     *
     * @param map
     * @return
     */
    public static String generateToken(Map<String, Object> map, boolean rememberMe) {
        JWT jwt = JWT.create();
        // 设置携带数据
        map.forEach(jwt::setPayload);
        // 设置密钥
        jwt.setKey(JWT_SECRET);
        // 设置过期时间（默认一天，rememberMe == true则为7天）
        if (rememberMe) {
            jwt.setExpiresAt(new Date(System.currentTimeMillis() + JWT_TTL * 7));
        } else {
            jwt.setExpiresAt(new Date(System.currentTimeMillis() + JWT_TTL));
        }
        return jwt.sign();
    }

    /**
     * token校验
     *
     * @param token
     * @return boolean
     */
    public static boolean verify(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        } else {
            return JWT.of(token).setKey(JWT_SECRET).verify();
        }
    }

    /**
     * 从token获取role信息
     *
     * @param token
     * @return role
     */
    public static String getRoleFromToken(String token) {
        return JWT.of(token).setKey(JWT_SECRET).getPayload("role").toString();
    }

    /**
     * 从request获取用户名信息
     *
     * @param request
     * @return username
     */
    public static String getUsernameFromRequest(HttpServletRequest request) {
        String token = JwtAuthenticationTokenFilter.getJwtFromRequest(request);
        if (token == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return JWT.of(token).setKey(JWT_SECRET).getPayload("username").toString();
    }

    /**
     * 从request获取id信息
     *
     * @param request
     * @return
     */
    public static String getUserIdFromRequest(HttpServletRequest request) {
        String token = JwtAuthenticationTokenFilter.getJwtFromRequest(request);
        if (token == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return JWT.of(token).setKey(JWT_SECRET).getPayload("id").toString();
    }

    /**
     * 从request获取role信息
     *
     * @param request
     * @return
     */
    public static String getRoleFromRequest(HttpServletRequest request) {
        String token = JwtAuthenticationTokenFilter.getJwtFromRequest(request);
        if (token == null) {
            return null;
        }
        return JWT.of(token).setKey(JWT_SECRET).getPayload("role").toString();
    }
}
