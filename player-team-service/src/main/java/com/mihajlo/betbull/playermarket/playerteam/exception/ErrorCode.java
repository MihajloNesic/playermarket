package com.mihajlo.betbull.playermarket.playerteam.exception;

public enum ErrorCode {
    BASE_EXCEPTION(1000),
    ENTITY_EXCEPTION(1001),
    INPUT_EXCEPTION(1002);

    private Integer code;

    ErrorCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
