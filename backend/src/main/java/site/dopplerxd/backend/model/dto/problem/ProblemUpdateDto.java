package site.dopplerxd.backend.model.dto.problem;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 问题创建请求
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 21:29
 */
@Data
public class ProblemUpdateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    @NotNull(message = "题目ID不能为空")
    private Long id;

    @NotBlank(message = "自定义题号不能为空")
    private String problemId;

    @NotBlank(message = "题目标题不能为空")
    @Size(max = 100, message = "题目标题长度不能超过100")
    private String title;

    @Min(value = 0, message = "题目类型不合法")
    @Max(value = 1, message = "题目类型不合法")
    private Integer type;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Integer stackLimit;

    @Size(max = 10000, message = "题目描述长度不能超过10000")
    private String description;

    @Size(max = 10000, message = "输入描述长度不能超过10000")
    private String input;

    @Size(max = 10000, message = "输出描述长度不能超过10000")
    private String output;

    private String examples;

    private List<String> tags;

    private Integer isRemote;

    private String source;

    @Min(value = 0, message = "题目难度不合法")
    @Max(value = 3, message = "题目难度不合法")
    private Integer difficulty;

    private String hint;

    @Min(value = 1, message = "题目权限不合法")
    @Max(value = 3, message = "题目权限不合法")
    private Integer auth;

    private Integer ioScore;

    private Integer codeShare;

    private String judgeMode;

    private String judgeCaseMode;

    private String userExtraFile;

    private String judgeExtraFile;

    private String spjCode;

    private String spjLanguage;

    private Integer isRemoveEndBlank;

    private Integer openCaseResult;

    private Integer isUploadCase;
}
