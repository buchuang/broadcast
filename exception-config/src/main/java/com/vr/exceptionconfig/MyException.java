package com.vr.exceptionconfig;


import com.vr.commonutils.utils.ErrorEnum;

public class MyException extends RuntimeException {
    private Integer code;
    private String message;

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
        this.message=message;
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }
    public MyException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.message=message;
        this.code=errorEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
