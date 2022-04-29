package com.lh.blog.common.domain;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 21:22
 */
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;

    CommonResult(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CommonResult<T> common(int code , String message , T data){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(code);
        commonResult.setMessage(message);
        commonResult.setData(data);
        return commonResult;
    }

    //两个参数，返回的数据默认为null
    public static <T> CommonResult<T> success(){
        return common(Status.SUCCESS.code(),"请求相应成功",null);
    }


    /**
     *
     *  根据自定义的message和data进行返回
     * @param message 需要返回的相应消息
     * @param data 需要返回的数据
     * @param <T> CommonResult<T>
     * @return
     */
    public static <T> CommonResult<T> success(String message,T data){
        return common(Status.SUCCESS.code(),message,data);
    }

    public static <T> CommonResult<T> notFount(String message , T data){
        return common(Status.NOE_FOUNT.code(),message,data);
    }

    public static <T> CommonResult<T> failure(String message , T data){
        return common(Status.FAILURE.code(),message,data);
    }



}
