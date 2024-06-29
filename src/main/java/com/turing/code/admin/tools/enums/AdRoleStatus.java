package com.turing.code.admin.tools.enums;

/**
 * @author Turing
 */
public enum AdRoleStatus {

    FREEZE("冻结", -1),

    NORMAL("正常", 1);

    private String label;

    private Integer value;

    AdRoleStatus(String label, Integer value) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
