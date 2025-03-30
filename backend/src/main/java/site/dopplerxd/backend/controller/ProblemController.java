package site.dopplerxd.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.judge.DoJudgeService;
import site.dopplerxd.backend.model.dto.judge.JudgeQueryDto;
import site.dopplerxd.backend.model.dto.judge.JudgeSubmitDto;
import site.dopplerxd.backend.model.dto.problem.ProblemCreateDto;
import site.dopplerxd.backend.model.dto.problem.ProblemDeleteDto;
import site.dopplerxd.backend.model.dto.problem.ProblemQueryDto;
import site.dopplerxd.backend.model.dto.problem.ProblemUpdateDto;
import site.dopplerxd.backend.model.enmus.JudgeSubmitLanguage;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.JudgeVO;
import site.dopplerxd.backend.model.vo.ProblemEditVO;
import site.dopplerxd.backend.model.vo.ProblemVO;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.service.ProblemService;
import site.dopplerxd.backend.utils.JwtUtils;

import java.util.List;
import java.util.Objects;

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

    @Resource
    private JudgeService judgeService;

    @Resource
    private DoJudgeService doJudgeService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String PROBLEM_CACHE_KEY = "codespace:cache:problemvo:";

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
        // 如果缓存中有这个题目，则删除缓存
        stringRedisTemplate.delete(PROBLEM_CACHE_KEY + problem.getProblemId());
        return ResultUtils.success(true);
    }

    /**
     * 删除题目（创建者或管理员）
     *
     * @param problemDeleteDto
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @PostMapping("/delete")
    public BaseResponse<Boolean> problemDelete(@RequestBody ProblemDeleteDto problemDeleteDto, HttpServletRequest request) {
        String pid = problemDeleteDto.getProblemId();
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 管理员或题目创建者才能删除
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", pid);
        Problem problem = problemService.getOne(queryWrapper);
        if (problem == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        String username = JwtUtils.getUsernameFromRequest(request);
        String role = JwtUtils.getRoleFromRequest(request);
        if (!username.equals(problem.getAuthor()) && !"admin".equals(role)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(problemService.removeById(problem.getId()));
    }

    /**
     * 获取指定题目
     *
     * @param pid
     * @return
     */
    @GetMapping("/get/summary/{pid}")
    public BaseResponse<ProblemVO> problemGet(@PathVariable("pid") String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProblemVO problemVO = problemService.getByPid(pid);
        return ResultUtils.success(problemVO);
    }

    /**
     * 获取题目详细信息（编辑）（创建者或管理员）
     *
     * @param pid
     * @param request
     * @return
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    @GetMapping("/get/detail/{pid}")
    public BaseResponse<ProblemEditVO> problemGetDetail(@PathVariable("pid") String pid, HttpServletRequest request) {
        System.out.println("pid: " + pid);
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProblemEditVO problemEditVO = problemService.getDetailByPid(pid);
        String username = JwtUtils.getUsernameFromRequest(request);
        String role = JwtUtils.getRoleFromRequest(request);
        if (!username.equals(problemEditVO.getAuthor()) && !"admin".equals(role)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(problemEditVO);
    }

    /**
     * 获取题目列表
     *
     * @param current
     * @param difficulty
     * @param tags 传入形式："tag1","tag2"    Spring会自动包装成List<String>
     * @param keyword
     * @param request 返回List<problems>、total、pages
     * @return
     */
    @GetMapping("/get/problem/list")
    public BaseResponse<JSONObject> problemGetList(
            @RequestParam(value = "current", defaultValue = "1", required = false) int current,
            @RequestParam(value = "difficulty", required = false) Integer difficulty,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "keyword", required = false) String keyword,
            HttpServletRequest request) {
        String userId = null;
        if (JwtUtils.isLogin(request)) {
            userId = JwtUtils.getUserIdFromRequest(request);
        }
        ProblemQueryDto queryDto = new ProblemQueryDto(current, difficulty, tags, keyword);
        return ResultUtils.success(problemService.getProblemList(queryDto, userId));
    }

    /**
     * 提交判题
     *
     * @param judgeSubmitDto
     * @param request
     * @return
     */
    @PostMapping("/submit/judge")
    public BaseResponse<Long> judgeSubmit(@Validated @RequestBody JudgeSubmitDto judgeSubmitDto, HttpServletRequest request) {
        if (judgeSubmitDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println("judgeSubmit success " + judgeSubmitDto);

        Judge judge = new Judge();
        BeanUtil.copyProperties(judgeSubmitDto, judge);
        System.out.println("judge: " + judge);

        String userId = JwtUtils.getUserIdFromRequest(request);
        if (userId == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        System.out.println("before into validJudge");
        judgeService.validJudge(judge);
        System.out.println("validJudge success " + judge.getPid());

        judge.setUsername(JwtUtils.getUsernameFromRequest(request));
        judge.setUid(JwtUtils.getUserIdFromRequest(request));
        judge.setLength(judge.getCode().length());
        judge.setLanguage(Objects.requireNonNull(JudgeSubmitLanguage.getEnumByValue(judge.getLanguage())).getLanguage());
        judge.setStatus(-9);
        boolean result = judgeService.save(judge);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newJudgeId = judge.getSubmitId();
        System.out.println("newJudgeId: " + newJudgeId);
        // TODO: 执行判题服务
        doJudgeService.doJudge(newJudgeId);
        return ResultUtils.success(newJudgeId);
    }

    /**
     * 根据ID获取提交详情
     *
     * @param judgeId
     * @return
     */
    @GetMapping("/get/judge/{judgeId}")
    public BaseResponse<JudgeVO> judgeGet(@PathVariable("judgeId") long judgeId, HttpServletRequest request) {
        String userId = JwtUtils.getUserIdFromRequest(request);
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        JudgeVO judgeVO = judgeService.getJudgeDetailById(judgeId, userId);
        return ResultUtils.success(judgeVO);
    }

    /**
     * 获取提交列表
     *
     * @param current
     * @param problemId
     * @param username
     * @param status
     * @param language
     * @param request
     * @return
     */
    @GetMapping("/get/judge/list")
    public BaseResponse<JSONObject> judgeGetList(
            @RequestParam(value = "current", defaultValue = "1", required = false) int current,
            @RequestParam(value = "problemId", required = false) Long problemId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "language", required = false) String language,
            HttpServletRequest request) {
        JudgeQueryDto queryDto = new JudgeQueryDto();
        queryDto.setCurrent(current);
        queryDto.setPid(problemId);
        queryDto.setUsername(username);
        queryDto.setStatus(status);
        queryDto.setLanguage(language);

        return ResultUtils.success(judgeService.getJudgeList(queryDto));
    }
}
