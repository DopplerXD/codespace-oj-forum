package site.dopplerxd.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.model.dto.problem.ProblemCreateDto;
import site.dopplerxd.backend.model.dto.problem.ProblemQueryDto;
import site.dopplerxd.backend.model.dto.problem.ProblemUpdateDto;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.ProblemVO;
import site.dopplerxd.backend.service.ProblemService;
import site.dopplerxd.backend.utils.JwtUtils;

import java.util.List;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 21:24
 */
@RestController
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 创建题目
     *
     * @param problemCreateDto
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @PostMapping ("/create")
    public BaseResponse<Long> problemCreate(@Validated @RequestBody ProblemCreateDto problemCreateDto, HttpServletRequest request) {
        if (problemCreateDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemCreateDto.getProblemId());
        if (problemService.exists(queryWrapper)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目ID已存在");
        }
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemCreateDto, problem);
        List<String> tags = problemCreateDto.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        problem.setAuthor(JwtUtils.getUsernameFromRequest(request));
        problem.setModifiedUser(JwtUtils.getUsernameFromRequest(request));
        try {
            problemService.save(problem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建题目失败");
        }
        long newProblemId = problem.getId();
        return ResultUtils.success(newProblemId);
    }

    /**
     * 修改题目（创建者或管理员）
     *
     * @param problemUpdateDto
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @PostMapping("/update")
    public BaseResponse<Boolean> problemUpdate(@Validated @RequestBody ProblemUpdateDto problemUpdateDto, HttpServletRequest request) {
        if (problemUpdateDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 管理员或题目创建者才能修改
        Problem problem = problemService.getById(problemUpdateDto.getId());
        if (problem == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        String username = JwtUtils.getUsernameFromRequest(request);
        String role = JwtUtils.getRoleFromRequest(request);
        if (!username.equals(problem.getAuthor()) && !"admin".equals(role)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemUpdateDto.getProblemId());
        if (problemService.exists(queryWrapper)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目ID已存在");
        }
        BeanUtil.copyProperties(problemUpdateDto, problem);
        List<String> tags = problemUpdateDto.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        problem.setModifiedUser(username);
        try {
            problemService.updateById(problem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改题目失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 删除题目（创建者或管理员）
     *
     * @param pid
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @PostMapping("/delete")
    public BaseResponse<Boolean> problemDelete(@RequestBody String pid, HttpServletRequest request) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 管理员或题目创建者才能删除
        Problem problem = problemService.getById(pid);
        if (problem == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        String username = JwtUtils.getUsernameFromRequest(request);
        String role = JwtUtils.getRoleFromRequest(request);
        if (!username.equals(problem.getAuthor()) && !"admin".equals(role)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(problemService.removeById(pid));
    }

    /**
     * 获取指定题目
     *
     * @param pid
     * @return
     */
    @GetMapping("/{pid}")
    public BaseResponse<ProblemVO> problemGet(@PathVariable("pid") String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProblemVO problemVO = problemService.getByPid(pid);
        return ResultUtils.success(problemVO);
    }

    /**
     * 获取题目列表，支持条件分页查询
     *
     * @param queryDto 题目查询条件
     * @param request HttpServletRequest
     * @return 包含题目列表的 BaseResponse
     */
    @GetMapping("/list")
    public BaseResponse<JSONObject> problemGetList(@Validated @RequestParam ProblemQueryDto queryDto, HttpServletRequest request) {
        return ResultUtils.success(problemService.getProblemList(queryDto, JwtUtils.getUserIdFromRequest(request)));
    }
}
