package site.dopplerxd.backend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 14:59
 */
public class JudgeVO implements Serializable {

    @TableId(value = "submit_id", type = IdType.ASSIGN_ID)
    private Long submitId;

    /**
     * 题目id
     */
    @TableField(value = "pid")
    private Long pid;

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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
