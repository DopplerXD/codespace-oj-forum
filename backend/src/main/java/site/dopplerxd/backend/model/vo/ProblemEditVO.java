package site.dopplerxd.backend.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 题目编辑信息VO类
 *
 * @author doppleryxc
 * @TableName problem
 */
@Data
public class ProblemEditVO implements Serializable {

    private Long id;

    private String problemId;

    /**
     * 题目
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 0为ACM,1为OI
     */
    private Integer type;

    /**
     * 单位ms
     */
    private Integer timeLimit;

    /**
     * 单位kb
     */
    private Integer memoryLimit;

    /**
     * 单位mb
     */
    private Integer stackLimit;

    /**
     * 描述
     */
    private String description;

    /**
     * 输入描述
     */
    private String input;

    /**
     * 输出描述
     */
    private String output;

    /**
     * 题面样例
     */
    private String examples;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 是否为vj判题
     */
    private Integer isRemote;

    /**
     * 题目来源
     */
    private String source;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    private Integer difficulty;

    /**
     * 备注,提醒
     */
    private String hint;

    /**
     * 默认为1公开，2为私有，3为比赛题目
     */
    private Integer auth;

    /**
     * 当该题目为io题目时的分数
     */
    private Integer ioScore;

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    private Integer codeShare;

    /**
     * 题目评测模式,default、spj、interactive
     */
//    private String judgeMode;

    /**
     * 题目样例评测模式,default,subtask_lowest,subtask_average
     */
//    private String judgeCaseMode;

    /**
     * 题目评测时用户程序的额外文件 json key:name value:content
     */
//    private String userExtraFile;

    /**
     * 题目评测时交互或特殊程序的额外文件 json key:name value:content
     */
//    private String judgeExtraFile;

    /**
     * 特判程序或交互程序代码
     */
//    private String spjCode;

    /**
     * 特判程序或交互程序代码的语言
     */
//    private String spjLanguage;

    /**
     * 是否默认去除用户代码的文末空格
     */
//    private Integer isRemoveEndBlank;

    /**
     * 是否默认开启该题目的测试样例结果查看
     */
//    private Integer openCaseResult;

    /**
     * 题目测试数据是否是上传文件的
     */
//    private Integer isUploadCase;

    @Serial
    private static final long serialVersionUID = 1L;
}
