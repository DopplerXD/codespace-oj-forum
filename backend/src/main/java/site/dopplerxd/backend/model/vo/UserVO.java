package site.dopplerxd.backend.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/19 10:33
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String profile;

    /**
     * 用户角色：user/admin/ban
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}
