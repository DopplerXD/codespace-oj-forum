package site.dopplerxd.backend.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/19 11:27
 */
@Data
public class UserLoginDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String username;

    private String password;

    /**
     * 是否七天内自动登录
     */
    private boolean rememberMe;
}
