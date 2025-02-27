package site.dopplerxd.backend.model.dto.problem;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/27 15:17
 */
@Data
public class ProblemQueryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 当前页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private int current;

    /**
     * 难度
     */
    private Integer difficulty;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 搜索关键词
     */
    private String keyword;
}
