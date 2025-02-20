package site.dopplerxd.backend.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.mapper.UserMapper;
import site.dopplerxd.backend.model.entity.User;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 10:48
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with user account: " + userAccount);
        }

        // 创建并返回 UserDetails 对象
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserAccount())
                .password(user.getUserPassword())
                .roles(user.getUserRole()) // 这里简单设置角色为 USER，可根据实际情况调整
                .build();
    }
}
