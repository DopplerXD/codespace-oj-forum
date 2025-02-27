package site.dopplerxd.backend;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.dopplerxd.backend.test.RedisConnectionTester;

@SpringBootTest
class BackendApplicationTests {

    @Resource
    RedisConnectionTester redisConnectionTester;

    @Test
    void contextLoads() {
        boolean isConnected = redisConnectionTester.testConnection();
        if (isConnected) {
            System.out.println("成功连接到 Redis");
        } else {
            System.out.println("无法连接到 Redis");
        }
    }

}
