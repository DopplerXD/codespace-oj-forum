package site.dopplerxd.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评测实体类
 *
 * @author doppleryxc
 * @TableName judge
 */
@TableName(value ="judge")
@Data
public class Judge implements Serializable {
    /**
     * 
     */
    @TableId(value = "submit_id", type = IdType.ASSIGN_ID)
    private Long submitId;

    /**
     * 题目id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 题目展示id
     */
    @TableField(value = "display_pid")
    private String displayPid;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 比赛id，非比赛题目默认为0
     */
    @TableField(value = "cid")
    private Long cid;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 提交的时间
     */
    @TableField(value = "submit_time")
    private Date submitTime;

    /**
     * 结果码具体参考文档
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 0为仅自己可见，1为全部人可见。
     */
    @TableField(value = "share")
    private Integer share;

    /**
     * 错误提醒（编译错误，或者vj提醒）
     */
    @TableField(value = "error_message")
    private String errorMessage;

    /**
     * 运行时间(ms)
     */
    @TableField(value = "time")
    private Integer time;

    /**
     * 运行内存（kb）
     */
    @TableField(value = "memory")
    private Integer memory;

    /**
     * IO判题则不为空
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 代码长度
     */
    @TableField(value = "length")
    private Integer length;

    /**
     * 代码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 代码语言
     */
    @TableField(value = "language")
    private String language;

    /**
     * 比赛中题目排序id，非比赛题目默认为0
     */
    @TableField(value = "cpid")
    private Long cpid;

    /**
     * vjudge判题在其它oj的提交id
     */
    @TableField(value = "vjudge_submit_id")
    private Long vjudgeSubmitId;

    /**
     * vjudge判题在其它oj的提交用户名
     */
    @TableField(value = "vjudge_username")
    private String vjudgeUsername;

    /**
     * vjudge判题在其它oj的提交账号密码
     */
    @TableField(value = "vjudge_password")
    private String vjudgePassword;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}