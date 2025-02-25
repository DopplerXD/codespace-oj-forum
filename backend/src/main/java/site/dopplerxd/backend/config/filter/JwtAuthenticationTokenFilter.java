package site.dopplerxd.backend.config.filter;

import cn.hutool.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.utils.JwtUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * JWT认证过滤器
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 10:25
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 执行过滤操作
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //  打印所有请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
//            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

        // 从请求头中获取 JWT
        String token = getJwtFromRequest(request);
//        System.out.println("JwtAuthenticationTokenFilter - " + token);

        if (StringUtils.isNotBlank(token) && JwtUtils.verify(token)) {
            try {
                // 解析 JWT 中的用户账号和角色信息
                String username = JWT.of(token).getPayload("username").toString();
                String role = JWT.of(token).getPayload("role").toString();

                // 根据用户账号获取用户详细信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 构建用户权限集合
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

                // 创建认证令牌
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 将认证信息设置到安全上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 处理 JWT 解析异常
                ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头中获取 JWT
     *
     * @param request
     * @return
     */
    public static String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(TOKEN_HEADER);
        if (StringUtils.isNotBlank(header) && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
