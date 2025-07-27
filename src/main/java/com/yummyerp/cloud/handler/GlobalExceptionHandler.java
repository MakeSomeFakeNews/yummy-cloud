package com.yummyerp.cloud.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.yummyerp.cloud.modules.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截：未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<Object> handlerNotLoginException(NotLoginException e) {
        log.error("未登录异常：{}", e.getMessage());
        return Result.failed(401, "未登录，请先登录");
    }

    /**
     * 拦截：缺少权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result<Object> handlerNotPermissionException(NotPermissionException e) {
        log.error("缺少权限异常：{}", e.getMessage());
        return Result.failed(403, "缺少权限：" + e.getMessage());
    }

    /**
     * 拦截：缺少角色异常
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<Object> handlerNotRoleException(NotRoleException e) {
        log.error("缺少角色异常：{}", e.getMessage());
        return Result.failed(403, "缺少角色：" + e.getMessage());
    }

    /**
     * 拦截：参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";
        return Result.validateFailed(message);
    }

    /**
     * 拦截：参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> handlerBindException(BindException e) {
        log.error("参数绑定异常：{}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数绑定失败";
        return Result.validateFailed(message);
    }

    /**
     * 拦截：其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handlerException(Exception e) {
        log.error("系统异常：", e);
        return Result.failed("系统异常：" + e.getMessage());
    }
}