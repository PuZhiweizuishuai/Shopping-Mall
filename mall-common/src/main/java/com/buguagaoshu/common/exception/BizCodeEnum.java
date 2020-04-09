package com.buguagaoshu.common.exception;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-09 19:40
 */
public enum BizCodeEnum {
    /**
     * 10 通用异常
     * 11 商品服务异常
     * 12 订单
     * 13 购物车
     * 14 物流
     * */
    UNKNOWN_EXCEPTION(10000, "系统未知异常！"),
    VALID_EXCEPTION(10001, "参数格式校验失败！");

    private int code;

    private String message;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
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
}
