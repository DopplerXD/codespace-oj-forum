package site.dopplerxd.backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 问题VO
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/23 10:32
 */
@Data
public class ProblemVO implements Serializable {

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
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 0为ACM,1为OI
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 单位ms
     */
    @TableField(value = "time_limit")
    private Integer timeLimit;

    /**
     * 单位kb
     */
    @TableField(value = "memory_limit")
    private Integer memoryLimit;

    /**
     * 单位mb
     */
    @TableField(value = "stack_limit")
    private Integer stackLimit;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 输入描述
     */
    @TableField(value = "input")
    private String input;

    /**
     * 输出描述
     */
    @TableField(value = "output")
    private String output;

    /**
     * 题面样例
     */
    @TableField(value = "examples")
    private String examples;

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
     * 是否为vj判题
     */
    @TableField(value = "is_remote")
    private Integer isRemote;

    /**
     * 题目来源
     */
    @TableField(value = "source")
    private String source;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    @TableField(value = "difficulty")
    private Integer difficulty;

    /**
     * 备注,提醒
     */
    @TableField(value = "hint")
    private String hint;

    /**
     * 默认为1公开，2为私有，3为比赛题目
     */
    @TableField(value = "auth")
    private Integer auth;

    /**
     * 当该题目为io题目时的分数
     */
    @TableField(value = "io_score")
    private Integer ioScore;

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    @TableField(value = "code_share")
    private Integer codeShare;

    /**
     * 题目评测模式,default、spj、interactive
     */
    @TableField(value = "judge_mode")
    private String judgeMode;

    /**
     * 题目样例评测模式,default,subtask_lowest,subtask_average
     */
    @TableField(value = "judge_case_mode")
    private String judgeCaseMode;

    @Serial
    private static final long serialVersionUID = 1L;
}
