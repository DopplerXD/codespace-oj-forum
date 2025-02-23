package site.dopplerxd.backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/23 11:07
 */
@Data
public class ProblemSummaryVO implements Serializable {

    /**
     * 问题的自定义ID 例如（HOJ-1000）
     */
    @TableField(value = "problem_id")
    private String problemId;

    /**
     * 题目
     */
    @TableField(value = "title")
    private String title;

    /**
     * 收藏数
     */
    @TableField(value = "favour_num")
    private Integer favourNum;

    /**
     * 提交数
     */
    @TableField(value = "submit_num")
    private Integer submitNum;

    /**
     * 通过数
     */
    @TableField(value = "accepted_num")
    private Integer acceptedNum;

    /**
     * 标签列表（json 数组）
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    @TableField(value = "difficulty")
    private Integer difficulty;

    @Serial
    private static final long serialVersionUID = 1L;
}
