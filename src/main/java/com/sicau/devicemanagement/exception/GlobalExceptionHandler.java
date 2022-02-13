package com.sicau.devicemanagement.exception;

import com.sicau.devicemanagement.common.constant.HttpStatus;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * 
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public AjaxResult handleAuthenticationException(AuthenticationException authenticationException,HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        log.info("请求地址：{}, {}",uri,authenticationException.getMessage());
        if (authenticationException instanceof UsernameNotFoundException || authenticationException instanceof BadCredentialsException) {
            return AjaxResult.error("用户名或密码错误");
        } else if (authenticationException instanceof DisabledException) {
            return AjaxResult.error("用户已被禁用");
        } else if (authenticationException instanceof LockedException) {
            return AjaxResult.error("账户被锁定");
        } else if (authenticationException instanceof AccountExpiredException) {
            return AjaxResult.error("账户过期");
        } else if (authenticationException instanceof CredentialsExpiredException) {
            return AjaxResult.error("证书过期");
        } else {
            return AjaxResult.error("登录失败");
        }
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常{}.", requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常{}.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 验证异常
     */
    @ExceptionHandler(CaptchaException.class)
    public AjaxResult handleCaptchaException(CaptchaException e,HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',系统发生异常{}.",requestURI,e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }




}
