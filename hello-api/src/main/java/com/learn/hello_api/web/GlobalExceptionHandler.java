package com.learn.hello_api.web;

import com.learn.hello_api.Result;
import com.learn.hello_api.exception.BizException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice   // ≈ 全局 Error Boundary：所有 Controller 的异常都归我接
public class GlobalExceptionHandler {

    // 接业务异常：翻译成统一 Result
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBiz(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    // 接校验失败：把校验错误翻译成人话
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));      // C03 的 Stream 又回来了
        return Result.fail(400, msg);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleUnknown(Exception e) {
        // 真实项目这里要 log.error 记录堆栈
        return Result.fail(500, "系统繁忙，请稍后重试");
    }
}