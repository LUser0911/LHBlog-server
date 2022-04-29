package com.lh.blog.common.exception;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/9 20:10
 * @description
 */
public class UserException extends RuntimeException {
    private String message;
    private Object data;

    public UserException(String message,Object data){
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
