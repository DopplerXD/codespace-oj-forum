package site.dopplerxd.backend.service;

import cn.hutool.json.JSONObject;
import site.dopplerxd.backend.model.dto.problem.ProblemQueryDto;
import site.dopplerxd.backend.model.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import site.dopplerxd.backend.model.vo.ProblemEditVO;
import site.dopplerxd.backend.model.vo.ProblemVO;

/**
* @author doppleryxc
* @description 针对表【problem】的数据库操作Service
* @createDate 2025-02-20 17:53:04
*/
public interface ProblemService extends IService<Problem> {

    ProblemVO getByPid(String pid);

    JSONObject getProblemList(ProblemQueryDto queryDto, String userId);

    ProblemEditVO getDetailByPid(String pid);
}
