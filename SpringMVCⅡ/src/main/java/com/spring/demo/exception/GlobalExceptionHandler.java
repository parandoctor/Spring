package com.spring.demo.exception;

import com.spring.demo.model.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器。
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * 用于集中处理所有 Controller 抛出的异常，返回统一的 JSON 错误响应。
 *
 * 异常处理优先级：本类的 @ExceptionHandler > @Controller 内的 @ExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理缺少必需请求参数的异常。
     * 触发条件：@RequestParam(required=true) 的参数未传递
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleMissingParam(MissingServletRequestParameterException ex) {
        return ApiResult.error(400, "缺少必需参数: " + ex.getParameterName());
    }

    /**
     * 处理文件大小超限异常。
     * 触发条件：上传的文件超过 maxFileSize 或 maxRequestSize
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ApiResult<Void> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ApiResult.error(413, "上传文件过大，单文件最大 10MB，请求最大 20MB");
    }

    /**
     * 处理非法参数异常。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ApiResult.error(400, ex.getMessage());
    }

    /**
     * 兜底异常处理：处理所有未被上述方法捕获的异常。
     * 生产环境不建议直接把异常堆栈返回给前端。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<Void> handleException(Exception ex) {
        ex.printStackTrace();  // 生产环境改用日志框架
        return ApiResult.error(500, "服务器内部错误: " + ex.getMessage());
    }
}
