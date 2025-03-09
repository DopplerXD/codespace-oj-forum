package site.dopplerxd.backend.judge.codesandbox.model;

import lombok.Data;

import java.util.List;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 11:31
 */
@Data
public class ExecuteCodeResponse {

    private List<String> outputList;

    private String message;

    private Integer status;

    private Long time;

    private Long memory;
}
