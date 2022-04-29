package com.lh.blog.common.domain;

/**
 * @EnumName Status
 * @Description TODO
 * @Author 我勒个去
 * @Date 2022/3/30 21:08
 * @Version 1.0v
 **/
public enum Status {
    SUCCESS(200),
    NOE_FOUNT(404),
    FAILURE(500);

    private int code;

    Status(int code){
        this.code = code;
    }

    public int code(){
        return this.code;
    }


}
