package com.turing.code.common.utils.returnbody;

/**
 * 请求结果消息
 *
 * @author Turing
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
