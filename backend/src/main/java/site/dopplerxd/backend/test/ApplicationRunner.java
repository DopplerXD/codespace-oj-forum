package site.dopplerxd.backend.test;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 15:55
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private RedisConnectionTester redisConnectionTester;

    @Override
    public void run(String... args) throws Exception {
        boolean isConnected = redisConnectionTester.testConnection();
        if (isConnected) {
            System.out.println("成功连接到 Redis");
        } else {
            System.out.println("无法连接到 Redis");
        }
    }
}
