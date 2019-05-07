package com.vr.commonutils.utils;

public enum ErrorEnum {

    TOKEN_VAILD(10001,"用户SESSION失效"),
    REQUEST_FAILED(10002,"请求失败"),


    /**
     * 通用异常
     */
    REQUEST_PARAM_FAILED(20001,"参数不能为空"),


    /**
     * 用户相关
     */
    USER_NOT_EXIST(30001,"用户不存在"),
    PASSWORD_NOT_RIGHT(30002,"密码不正确"),
    GET_USERINFO_FAIL(30003,"获取用户信息失败"),


    /**
     * 数据库相关
     */
    DATABASE_ERROR(40001,"数据库异常"),
    REDIS_ERROR(40001,"redis异常"),


    /**
     * 房间相关
     */
    ROOM_NUM_NOTNULL(500001,"房间号不能为空"),
    PLATEID_NOTNULL(500002,"直播板块不能为空"),
    ROOMTITLE_NOTNULL(500002,"房间标题不能为空"),
    ;
    private Integer code;
    private String message;


    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
