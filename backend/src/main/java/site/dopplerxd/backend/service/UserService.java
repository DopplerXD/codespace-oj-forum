package site.dopplerxd.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import site.dopplerxd.backend.model.entity.User;
import site.dopplerxd.backend.model.vo.LoginUserVO;

import java.util.Map;

/**
 * @author doppleryxc
 * @description 针对表【user】的数据库操作Service
 * @createDate 2025-02-20 17:43:59
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param checkPassword
     * @param request
     * @return
     */
    String userRegister(String username, String password, String checkPassword, HttpServletRequest request);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    Map<String, Object> userLogin(String username, String password, HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);
}
