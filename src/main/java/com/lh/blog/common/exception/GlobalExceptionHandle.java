package com.lh.blog.common.exception;

import com.lh.blog.common.domain.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/9 19:57
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(UserException.class)
    public CommonResult handleUserException(HttpServletRequest request,Throwable ex){
        //如果可以还可以进行切面的处理=>log记录
        UserException userException = (UserException) ex;
        return CommonResult.failure(userException.getMessage(),userException.getData());
    }

    @ExceptionHandler(RequestException.class)
    public CommonResult handleRequestException(HttpServletRequest request,Throwable ex){
        //如果可以还可以进行切面的处理=>log记录
        RequestException requestException = (RequestException) ex;
        return CommonResult.failure(requestException.getMessage(),requestException.getData());
    }


    @ExceptionHandler(ServerException.class)
    public CommonResult handleServerException(HttpServletRequest request,Throwable ex){
        //如果可以还可以进行切面的处理=>log记录
        ServerException serverException = (ServerException) ex;
        return CommonResult.failure(serverException.getMessage(),serverException.getData());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult handleException(HttpServletRequest request,Throwable ex){
        //如果可以还可以进行切面的处理=>log记录
        return CommonResult.failure(ex.getMessage(),null);
    }
}





