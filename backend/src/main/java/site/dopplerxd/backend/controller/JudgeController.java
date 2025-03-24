package site.dopplerxd.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.exception.ThrowUtils;
import site.dopplerxd.backend.model.dto.judge.JudgeQueryDto;
import site.dopplerxd.backend.model.dto.judge.JudgeSubmitDto;
import site.dopplerxd.backend.model.enmus.JudgeSubmitLanguage;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.vo.JudgeVO;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.utils.JwtUtils;

import java.util.Objects;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/21 16:19
 */
@RestController
//@RequestMapping("/judge")
@Slf4j
@Deprecated
public class JudgeController {

    @Resource
    private JudgeService judgeService;

    /**
     * 提交判题
     *
     * @param judgeSubmitDto
     * @param request
     * @return
     */
    @PostMapping("/submit")
    public BaseResponse<Long> submit(@Validated @RequestBody JudgeSubmitDto judgeSubmitDto, HttpServletRequest request) {
        if (judgeSubmitDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Judge judge = new Judge();
        BeanUtil.copyProperties(judgeSubmitDto, judge);
        judgeService.validJudge(judge);
        judge.setUsername(JwtUtils.getUsernameFromRequest(request));
        judge.setUid(JwtUtils.getUserIdFromRequest(request));
        judge.setLength(judge.getCode().length());
        judge.setLanguage(Objects.requireNonNull(JudgeSubmitLanguage.getEnumByValue(judge.getLanguage())).getLanguage());
        judge.setStatus(-9);
        boolean result = judgeService.save(judge);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newJudgeId = judge.getSubmitId();
        // TODO: 执行判题服务
        return ResultUtils.success(newJudgeId);
    }

    /**
     * 根据ID获取提交
     * @param judgeId
     * @return
     */
    @GetMapping("/get/{judgeId}")
    public BaseResponse<JudgeVO> judgeGet(@PathVariable("judgeId") long judgeId) {
        Judge judge = judgeService.getById(judgeId);
        if (judge == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        JudgeVO judgeVO = new JudgeVO();
        BeanUtil.copyProperties(judge, judgeVO);
        return ResultUtils.success(judgeVO);
    }

    /**
     * 获取提交列表
     * @param judgeQueryDto
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<JSONObject> judgeGetList(@Validated @RequestParam JudgeQueryDto judgeQueryDto, HttpServletRequest request) {
        return ResultUtils.success(judgeService.getJudgeList(judgeQueryDto));
    }
}
