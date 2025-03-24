package site.dopplerxd.backend.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.model.dto.problem.ProblemQueryDto;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.ProblemEditVO;
import site.dopplerxd.backend.model.vo.ProblemSummaryVO;
import site.dopplerxd.backend.model.vo.ProblemVO;
import site.dopplerxd.backend.service.JudgeService;
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

    @Resource
    private JudgeService judgeService;

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
    public ProblemEditVO getDetailByPid(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", pid);
        Problem problem = this.getOne(queryWrapper);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        ProblemEditVO problemEditVO = new ProblemEditVO();
        BeanUtils.copyProperties(problem, problemEditVO);
        // TODO: 将 json 字符串数组 转为 List<String>
        return problemEditVO;
    }

    @Override
    public JSONObject getProblemList(ProblemQueryDto queryDto, String userId) {
        int current = queryDto.getCurrent() > 0 ? queryDto.getCurrent() : 1; // 默认查第一页
        Integer difficulty = queryDto.getDifficulty();
        List<String> tags = queryDto.getTags();
        String keyword = queryDto.getKeyword();

        // 构建查询条件
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("problem_id");

        if (difficulty != null) {
            queryWrapper.eq("difficulty", difficulty);
        }
        if (tags!= null &&!tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0) {
                    queryWrapper.or();
                }
                queryWrapper.like("tags", tags.get(i));
            }
        }
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("title", keyword).or().like("description", keyword);
        }

        IPage<Problem> problemPage = new Page<>(current, 30);
        IPage<Problem> pageResult = this.page(problemPage, queryWrapper);
        List<Problem> problems = pageResult.getRecords();
        List<ProblemSummaryVO> items = new LinkedList<>();

        for (Problem problem : problems) {
            ProblemSummaryVO problemSummaryVO = new ProblemSummaryVO();
            BeanUtils.copyProperties(problem, problemSummaryVO);

            // 判断提交状态
            if (userId == null) {
                problemSummaryVO.setStatus(0);
            } else {
                QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
                judgeQueryWrapper.eq("pid", problem.getProblemId());
                judgeQueryWrapper.eq("uid", userId);
                boolean exists = judgeService.exists(judgeQueryWrapper);
                if (exists) {
                    judgeQueryWrapper.eq("status", 0);
                    boolean isSolved = judgeService.exists(judgeQueryWrapper);
                    problemSummaryVO.setStatus(isSolved ? 1 : -1);
                } else {
                    problemSummaryVO.setStatus(0);
                }
            }

            items.add(problemSummaryVO);
        }

        JSONObject res = new JSONObject();
        res.set("problems", items);
        res.set("total", pageResult.getTotal());
        res.set("pages", pageResult.getPages());
        return res;
    }
}




