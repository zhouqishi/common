package com.stone.entity.result;

import com.stone.enums.BaseResultEnum;
import com.stone.enums.DefaultResultEnum;

import java.io.Serializable;

/**
 * 通用返回
 * @author yuanxiu
 * @date 2020/11/7
 */
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public Result() {
        // 无参构造 Result
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> newSuccess() {
        return new Result<T>(DefaultResultEnum.success.getKey(), "Success", null);
    }

    public static <T> Result<T> newSuccess(T data) {
        return new Result<T>(DefaultResultEnum.success.getKey(), "Success", data);
    }

    public static <T> Result<T> newFailure(int errorCode, String errorMsg) {
        return new Result<T>(errorCode, errorMsg, null);
    }

    public static <T> Result<T> newFailure(BaseResultEnum resultEnum) {
        return new Result<T>(resultEnum.getKey(), resultEnum.getValue(), null);
    }

    public static <T> Result<T> newFailure(String errorMsg) {
        return new Result<T>(DefaultResultEnum.failure.getKey(), errorMsg, null);
    }

    public static <T> Result<T> newResult(BaseResultEnum resultEnum) {
        return new Result<T>(resultEnum.getKey(), resultEnum.getValue(), null);
    }

    public static <T> Result<T> newResult(BaseResultEnum resultEnum, T data) {
        return new Result<T>(resultEnum.getKey(), resultEnum.getValue(), data);
    }

}
