package com.leepsmart.code.common.swagger.apifox;

public enum ApiFoxVersion {
    V1("2024-01-80"),
    V2("2024-03-28");

    private final String value;

    private ApiFoxVersion(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
