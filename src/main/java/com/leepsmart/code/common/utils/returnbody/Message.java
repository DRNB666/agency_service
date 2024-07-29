package com.leepsmart.code.common.utils.returnbody;

/**
 * 请求结果消息
 *
 * @author leepsmart
 */
public enum Message {

    SUCCESS("success"), ERROR("error");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }

}
