package site.dopplerxd.backend.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.model.dto.judge.JudgeQueryDto;
import site.dopplerxd.backend.model.enmus.JudgeSubmitLanguage;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.JudgeSummaryVO;
import site.dopplerxd.backend.model.vo.JudgeVO;
import site.dopplerxd.backend.model.vo.ProblemSummaryVO;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.mapper.JudgeMapper;
import org.springframework.stereotype.Service;
import site.dopplerxd.backend.mapper.ProblemMapper;

import java.util.LinkedList;
import java.util.List;

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
        System.out.println("checking");

        Long id = judge.getPid();
        Problem problem = problemMapper.selectById(id);
        ThrowUtils.throwIf(problem == null, ErrorCode.PARAMS_ERROR, "题目不存在");

        JudgeSubmitLanguage language = JudgeSubmitLanguage.getEnumByValue(judge.getLanguage());
        ThrowUtils.throwIf(language == null, ErrorCode.PARAMS_ERROR, "不支持该语言");

        if (judge.getCode().length() > 100000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码长度超过限制");
        }
        System.out.println("judge: " + judge);
    }

    // TODO: 测评结果条件查询
    @Override
    public JSONObject getJudgeList(JudgeQueryDto queryDto) {
        System.out.println("queryDto: " + queryDto);
        int current = queryDto.getCurrent() > 0 ? queryDto.getCurrent() : 1; // 默认查第一页
        Long pid = queryDto.getPid();
        String username = queryDto.getUsername();
        Integer status = queryDto.getStatus();
        String language = queryDto.getLanguage();

        // 构建查询条件
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        if (pid != null) {
            queryWrapper.eq("pid", pid);
        }
        if (username != null &&!username.isEmpty()) {
            queryWrapper.eq("username", username);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (language != null &&!language.isEmpty()) {
            queryWrapper.eq("language", language);
        }

        IPage<Judge> judgePage = new Page<>(current, 30);
        IPage<Judge> judgePageResult = this.page(judgePage, queryWrapper);
        List<Judge> judges = judgePageResult.getRecords();
        List<JudgeSummaryVO> items = new LinkedList<>();

        for (Judge judge : judges) {
            JudgeSummaryVO judgeSummaryVO = new JudgeSummaryVO();
            BeanUtils.copyProperties(judge, judgeSummaryVO);
            judgeSummaryVO.setSubmitId(judge.getSubmitId().toString()); // 避免前端因为bigint导致精度丢失
            items.add(judgeSummaryVO);
        }

        JSONObject res = new JSONObject();
        res.set("judges", items);
        res.set("total", items.size());
        return res;
    }

    @Override
    public JudgeVO getJudgeDetailById(Long id, String userId) {
        Judge judge = this.getById(id);
        ThrowUtils.throwIf(judge == null, ErrorCode.NOT_FOUND_ERROR, "评测记录不存在");
        ThrowUtils.throwIf(judge.getShare() == 0
                && !judge.getUid().equals(userId), ErrorCode.NO_AUTH_ERROR, "作者未公开代码");
        JudgeVO judgeVO = new JudgeVO();
        BeanUtils.copyProperties(judge, judgeVO);
        return judgeVO;
    }
}




