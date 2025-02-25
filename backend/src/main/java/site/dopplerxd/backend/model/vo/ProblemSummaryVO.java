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
     * 题目状态（1表示AC，-1表示有错误提交，0表示无操作）
     */
    private Integer status;

    /**
     * 问题的自定义ID 例如（HOJ-1000）
     */
    private String problemId;

    /**
     * 题目
     */
    private String title;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 标签列表（json 数组）
     */
    private String tags;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    private Integer difficulty;

    @Serial
    private static final long serialVersionUID = 1L;
}
