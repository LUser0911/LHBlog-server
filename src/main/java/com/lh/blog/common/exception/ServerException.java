package com.lh.blog.common.exception;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/9 20:13
 * @description
 */
public class ServerException extends RuntimeException {
    private String message;
    private Object data;

    public ServerException(String message,Object data){
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
