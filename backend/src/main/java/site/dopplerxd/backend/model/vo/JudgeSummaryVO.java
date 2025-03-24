package site.dopplerxd.backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 14:59
 */
@Data
public class JudgeSummaryVO implements Serializable {

    private String submitId;

    /**
     * 题目id
     */
    @TableField(value = "pid")
    private Long pid;

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
     * 运行时间(ms)
     */
    @TableField(value = "time")
    private Long time;

    /**
     * 运行内存（kb）
     */
    @TableField(value = "memory")
    private Long memory;

    /**
     * IO判题则不为空
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 代码语言
     */
    @TableField(value = "language")
    private String language;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
