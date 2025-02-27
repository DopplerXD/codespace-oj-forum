package site.dopplerxd.backend.model.dto.judge;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 15:17
 */
@Data
public class JudgeQueryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private int current;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 比赛id，非比赛题目默认为0
     */
    private Long cid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 结果码具体参考文档
     */
    private Integer status;

    /**
     * 代码语言
     */
    private String language;
}
