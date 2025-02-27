package site.dopplerxd.backend.service;

import cn.hutool.json.JSONObject;
import site.dopplerxd.backend.model.dto.judge.JudgeQueryDto;
import site.dopplerxd.backend.model.entity.Judge;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author doppleryxc
* @description 针对表【judge】的数据库操作Service
* @createDate 2025-02-20 17:55:08
*/
public interface JudgeService extends IService<Judge> {

    public void validJudge(Judge judge);

    public JSONObject getJudgeList(JudgeQueryDto judgeQueryDto, String userId);

}
