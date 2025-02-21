package site.dopplerxd.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.model.enmus.JudgeSubmitLanguage;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.mapper.JudgeMapper;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.mapper.ProblemMapper;

/**
 * @author doppleryxc
 * @description 针对表【judge】的数据库操作Service实现
 * @createDate 2025-02-20 17:55:08
 */
@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge>
        implements JudgeService {

    @Resource
    private ProblemMapper problemMapper;

    @Override
    public void validJudge(Judge judge) {
        if (judge == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long cid = judge.getCid();
        // todo: 比赛功能实现
        if (cid != null && cid != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "比赛功能尚未实现");
        }

        Long pid = judge.getPid();
        Problem problem = problemMapper.selectById(pid);
        ThrowUtils.throwIf(problem == null, ErrorCode.PARAMS_ERROR, "题目不存在");

        JudgeSubmitLanguage language = JudgeSubmitLanguage.getEnumByValue(judge.getLanguage());
        ThrowUtils.throwIf(language == null, ErrorCode.PARAMS_ERROR, "不支持该语言");

        if (judge.getCode().length() > 100000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码长度超过限制");
        }
    }
}




