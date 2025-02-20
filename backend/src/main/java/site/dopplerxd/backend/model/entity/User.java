package site.dopplerxd.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户实体类
 * 
 * @author doppleryxc
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名（唯一）
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 学校
     */
    @TableField(value = "school")
    private String school;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * github地址
     */
    @TableField(value = "github")
    private String github;

    /**
     * 个人博客地址
     */
    @TableField(value = "blog")
    private String blog;

    /**
     * cf的username
     */
    @TableField(value = "cf_username")
    private String cfUsername;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 头像地址
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 个性签名
     */
    @TableField(value = "signature")
    private String signature;

    /**
     * 头衔、称号
     */
    @TableField(value = "title_name")
    private String titleName;

    /**
     * 头衔、称号的颜色
     */
    @TableField(value = "title_color")
    private String titleColor;

    /**
     * 用户角色：user/admin/ban
     */
    @TableField(value = "role")
    private String role;

    /**
     * 0可用，1不可用
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}