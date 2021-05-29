package com.example.demo.result;

import lombok.Getter;
import lombok.ToString;

/**
 * @author wk-ff
 */
@Getter
@ToString
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg){
        if(codeMsg == null){
            return;
        }
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
}
