package com.turing.code.common.utils.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;

/**
 * @author Turing
 */
public abstract class SmsSendConfig<T> {

    private String phone;

    public SmsSendConfig(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract void setSmsConfig(SendSmsRequest request);

    public abstract T success(String env);

    public abstract T error();

}
