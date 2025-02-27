package site.dopplerxd.backend.test;

/**
 * Redis连接测试类
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 15:50
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTester {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean testConnection() {
        try {
            // 写入一个简单的键值对
            String testKey = "testConnectionKey";
            String testValue = "testConnectionValue";
            redisTemplate.opsForValue().set(testKey, testValue);

            // 读取该键值对
            String retrievedValue = redisTemplate.opsForValue().get(testKey);

            // 删除测试键
            redisTemplate.delete(testKey);

            // 判断是否成功读取到值
            return testValue.equals(retrievedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
