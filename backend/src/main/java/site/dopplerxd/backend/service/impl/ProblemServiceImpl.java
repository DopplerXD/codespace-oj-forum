package site.dopplerxd.backend.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.ProblemSummaryVO;
import site.dopplerxd.backend.model.vo.ProblemVO;
import site.dopplerxd.backend.service.ProblemService;
import site.dopplerxd.backend.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author doppleryxc
 * @description 针对表【problem】的数据库操作Service实现
 * @createDate 2025-02-20 17:53:04
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
        implements ProblemService {

    @Override
    public ProblemVO getByPid(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", pid);
        Problem problem = this.getOne(queryWrapper);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        return problemVO;
    }

    @Override
    public JSONObject getProblemList(Integer current) {
        if (current == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        IPage<Problem> problemPage = new Page<>(current, 30);
        List<Problem> problems = this.page(problemPage).getRecords();
        List<ProblemSummaryVO> items = new LinkedList<>();
        for (Problem problem : problems) {
            ProblemSummaryVO problemSummaryVO = new ProblemSummaryVO();
            BeanUtils.copyProperties(problem, problemSummaryVO);
            items.add(problemSummaryVO);
        }
        JSONObject res = new JSONObject();
        res.set("problems", items);
        res.set("total", this.count());
        return res;
    }
}




