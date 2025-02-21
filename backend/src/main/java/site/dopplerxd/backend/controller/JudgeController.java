package site.dopplerxd.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
import site.dopplerxd.backend.model.dto.judge.JudgeSubmitDto;
import site.dopplerxd.backend.model.enmus.JudgeSubmitLanguage;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.utils.JwtUtils;

import java.util.Objects;

/**
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/21 16:19
 */
@RestController
@RequestMapping("/judge")
@Slf4j
public class JudgeController {

    @Resource
    private JudgeService judgeService;

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
        return ResultUtils.success(newJudgeId);
    }
}
