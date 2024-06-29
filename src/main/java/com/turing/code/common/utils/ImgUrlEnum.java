package com.turing.code.common.utils;


public enum ImgUrlEnum {

    SYSTEM_IMAGES("/system/images/"),

    FIRM_WARE("/firmware/"),
    TENANT_PRO_INVITE("/tenant/pro/"),
    TENANT_OTHER("/tenant/other/");

    private String path;

    ImgUrlEnum(String path) {
        this.path = path;
    }

    ImgUrlEnum() {
    }

    public String getPath() {
        return path;
    }
}
