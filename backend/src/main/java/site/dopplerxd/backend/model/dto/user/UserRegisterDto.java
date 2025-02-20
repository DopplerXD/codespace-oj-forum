package site.dopplerxd.backend.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 9:57
 */
@Data
public class UserRegisterDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String username;

    private String password;

    private String checkPassword;
}
