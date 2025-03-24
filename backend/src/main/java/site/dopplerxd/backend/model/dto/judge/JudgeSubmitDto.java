package site.dopplerxd.backend.model.dto.judge;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 题目提交请求
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/21 16:09
 */
@Data
public class JudgeSubmitDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 题目id
     */
    @NotNull(message = "题目ID不能为空")
    private Long pid;

//    /**
//     * 题目自定义id
//     */
//    private String pid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 0为仅自己可见，1为全部人可见。
     */
    @Min(value = 0, message = "提交是否公开不合法")
    @Max(value = 1, message = "提交是否公开不合法")
    private Integer share;

    /**
     * 代码
     */
    @NotBlank(message = "代码不能为空")
    private String code;

    /**
     * 代码语言
     */
    @NotBlank(message = "代码语言不能为空")
    private String language;

    /**
     * vjudge判题在其它oj的提交id
     */
    private Long vjudgeSubmitId;

    /**
     * vjudge判题在其它oj的提交用户名
     */
    private String vjudgeUsername;

    /**
     * vjudge判题在其它oj的提交账号密码
     */
    private String vjudgePassword;
}
