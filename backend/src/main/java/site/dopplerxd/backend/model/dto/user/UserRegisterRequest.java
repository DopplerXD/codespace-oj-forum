package site.dopplerxd.backend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/18 23:03
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
