package site.dopplerxd.backend.model.enmus;

/**
 * 提交状态
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/21 16:25
 */
public enum JudgeSubmitStatus {

    STATUS_NOT_SUBMITTED(-10, "未提交"),
    STATUS_SUBMITTING(9, "提交中"),
    STATUS_PENDING(6, "排队中"),
    STATUS_JUDGING(7, "评测中"),
    STATUS_COMPILE_ERROR(-2, "编译错误"),
    STATUS_PRESENTATION_ERROR(-3, "输出格式错误"),
    STATUS_WRONG_ANSWER(-1, "答案错误"),
    STATUS_ACCEPTED(0, "评测通过"),
    STATUS_TIME_LIMIT_EXCEEDED(1, "时间超限"),
    STATUS_MEMORY_LIMIT_EXCEEDED(3, "空间超限"),
    STATUS_RUNTIME_ERROR(4, "运行错误"),
    STATUS_SYSTEM_ERROR(5, "系统错误"),
    STATUS_SUBMITTED_FAILED(10, "提交失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String message;

    JudgeSubmitStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
