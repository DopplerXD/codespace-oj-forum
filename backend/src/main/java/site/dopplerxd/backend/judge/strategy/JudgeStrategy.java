package site.dopplerxd.backend.judge.strategy;

import java.util.Map;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/11 15:00
 */
public interface JudgeStrategy {

    Map<String, Object> doJudge(long submitId);
}
