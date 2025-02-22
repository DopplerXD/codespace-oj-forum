package site.dopplerxd.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.model.dto.problem.ProblemCreateDto;
import site.dopplerxd.backend.model.entity.Problem;
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

    @PreAuthorize("hasRole('admin')")
    @PostMapping ("/create")
    public BaseResponse<Long> problemCreate(@Validated @RequestBody ProblemCreateDto problemCreateDto, HttpServletRequest request) {
        if (problemCreateDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemCreateDto, problem);
        List<String> tags = problemCreateDto.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        problem.setAuthor(JwtUtils.getUsernameFromRequest(request));
        problem.setModifiedUser(JwtUtils.getUserIdFromRequest(request));
        boolean result = problemService.save(problem);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newProblemId = problem.getId();
        return ResultUtils.success(newProblemId);
    }
}
