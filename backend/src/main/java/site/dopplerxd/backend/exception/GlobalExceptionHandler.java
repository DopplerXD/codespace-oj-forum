package site.dopplerxd.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.dopplerxd.backend.common.BaseResponse;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.common.ResultUtils;

/**
 * 全局异常处理器
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/20 22:21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<String>> handleBindException(BindException e) {
        // 获取第一个校验错误信息
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        BaseResponse<String> response = new BaseResponse<>(ErrorCode.PARAMS_ERROR.getCode(), errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(BusinessException e) {
        return ResultUtils.error(e.getCode(), e.getMessage());
    }
}
