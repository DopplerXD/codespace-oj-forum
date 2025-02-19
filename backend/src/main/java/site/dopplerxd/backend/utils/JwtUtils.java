package site.dopplerxd.backend.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import site.dopplerxd.backend.model.dto.user.UserLoginDto;
import site.dopplerxd.backend.model.entity.User;
import site.dopplerxd.backend.model.vo.LoginUserVO;

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
    private static final long JWT_TTL = 1000 * 60 * 60 * 24 * 7;

    private JwtUtils() {
    }

    /**
     * 根据登录信息生成Token
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    public static String generateUserToken(Long id, String userAccount) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("userAccount", userAccount);
        return generateToken(map);
    }

    /**
     * 生成Token
     *
     * @param map
     * @return
     */
    public static String generateToken(Map<String, Object> map) {
        JWT jwt = JWT.create();
        // 设置携带数据
        map.forEach(jwt::setPayload);
        // 设置密钥
        jwt.setKey(JWT_SECRET);
        // 设置过期时间
        jwt.setExpiresAt(new Date(System.currentTimeMillis() + JWT_TTL));
        return jwt.sign();
    }

    /**
     * token校验
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        } else {
            return JWT.of(token).setKey(JWT_SECRET).verify();
        }
    }
}
