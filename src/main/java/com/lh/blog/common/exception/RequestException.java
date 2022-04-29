package com.lh.blog.common.exception;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/9 20:12
 * @description
 */
public class RequestException extends RuntimeException {
    private String message;
    private Object data;

    public RequestException(String message,Object data){
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
