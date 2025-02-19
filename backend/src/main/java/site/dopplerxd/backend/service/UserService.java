package site.dopplerxd.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import site.dopplerxd.backend.model.entity.User;
import site.dopplerxd.backend.model.vo.LoginUserVO;

import java.util.Map;

/**
 * 用户服务
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/19 10:39
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    Map<String, Object> userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);
}
