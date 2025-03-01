package site.dopplerxd.backend.model.dto.problem;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/1 14:37
 */
@Data
public class ProblemDeleteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String problemId;
}
