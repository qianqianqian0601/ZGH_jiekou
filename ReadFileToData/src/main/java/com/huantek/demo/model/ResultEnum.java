package com.huantek.demo.model;

/**
 * @author wyx
 */
public enum  ResultEnum {

    SERVER_EXCEPTION("1002","服务异常"),

    SUCCESS("0","成功"),

    PARAM_ERROR("1004","参数不合法"),

    DATABASE_ERROR("1005","数据库数据异常");


    private String code;
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

