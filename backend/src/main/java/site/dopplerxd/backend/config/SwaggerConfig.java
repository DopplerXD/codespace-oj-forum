package site.dopplerxd.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerOpenApi() {
        return new OpenAPI()
                .info(new Info().title("CodeSpace代码学习平台")
                        .description("基于 Knife4j OpenApi3 的测试接口文档")
                        .version("v1.0.0")
                        .contact(new Contact().name("DopplerYXC")
                                .email("1509209607@qq.com")));
    }
}