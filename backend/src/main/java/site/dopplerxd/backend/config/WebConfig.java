package site.dopplerxd.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/22 23:59
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许访问的路径
//                .allowedOrigins("*") // 允许的来源
                .allowedOrigins("http://localhost:8080", "http://localhost:8081") // 允许的来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowCredentials(true) // 是否允许发送 Cookie，为true时不能设置 Access-Control-Allow-Origin 为 *
                .allowedHeaders("*"); // 允许的请求头
    }
}
