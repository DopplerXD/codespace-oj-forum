package site.dopplerxd.backend.judge;

import site.dopplerxd.backend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 15:23
 */
public interface DoJudgeService {

    /**
     * 执行判题
     *
     * @param submitId
     * @return
     */
    ExecuteCodeResponse doJudge(long submitId);
}
