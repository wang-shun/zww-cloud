package com.stylefeng.guns.common.exception;

/**
 * Created by moying on 2018/6/26.
 */
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(BizExceptionEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
