package site.dopplerxd.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author doppleryxc
 */
@SpringBootApplication
@MapperScan("site.dopplerxd.backend.mapper")
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
