package com.turing.code.common.swagger.apifox;

public enum ApiFoxEndpointOverwriteBehavior {

    OVERWRITE_EXISTING("覆盖现有的接口","OVERWRITE_EXISTING"),
    AUTO_MERGE("自动合并更改到现有的接口","AUTO_MERGE"),
    KEEP_EXISTING("跳过更改并保留现有的接口。","KEEP_EXISTING"),
    CREATE_NEW("保留现有的接口，创建新的接口。","CREATE_NEW");

    private final String value;
    private final String remark;

    ApiFoxEndpointOverwriteBehavior(String remark,String value) {
        this.remark = remark;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
