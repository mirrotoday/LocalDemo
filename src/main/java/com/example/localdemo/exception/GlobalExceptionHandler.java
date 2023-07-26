package com.example.localdemo.exception;

import com.example.localdemo.result.ApiResult;
import com.google.protobuf.ServiceException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author xieteng
 * @date 2023/7/17 19:21
 * @description TODO
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    /**
     * 权限校验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult<?> handleAccessDeniedException(AccessDeniedException e,
                                                  HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},权限校验失败{}", requestURI, e.getMessage());
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 请求方式不支持
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                 HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},不支持{}请求", requestURI, e.getMethod());
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 参数验证失败异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                            HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("请求地址{},参数验证失败{}", requestURI, e.getObjectName(),e);
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 拦截错误SQL异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ApiResult<?> handleBadSqlGrammarException(BadSqlGrammarException e,
                                                   HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 可以拦截表示违反数据库的完整性约束导致的异常。
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResult<?> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                            HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return new ApiResult<>().error(e.getMessage());
    }


    /**
     * 可以拦截违反数据库的非完整性约束导致的异常，可能也会拦截一些也包括 SQL 语句错误、连接问题、权限问题等各种数据库异常。
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UncategorizedSQLException.class)
    public ApiResult<?> handleUncategorizedSqlException(UncategorizedSQLException e,
                                                      HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生数据库异常.", requestURI, e);
        return new ApiResult<>().error(e.getMessage());
    }
    @ExceptionHandler(SQLServerException.class)
    public ApiResult<?> handleSqlServerException(SQLServerException e,
                                                        HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生SQLServer数据库异常.", requestURI, e);
        return new ApiResult<>().error(e.getMessage());
    }
    /**
     * 拦截未知的运行时异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntimeException(RuntimeException e,
                                             HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生未知运行异常", requestURI, e);
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 业务自定义异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public ApiResult<?> handleServiceException(ServiceException e,
                                             HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生业务自定义异常{}",requestURI,e.getMessage(), e);
        return new ApiResult<>().error(e.getMessage());
    }

    /**
     * 全局异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e,
                                      HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生系统异常",requestURI,e);
        return new ApiResult<>().error(e.getMessage());
    }
}
