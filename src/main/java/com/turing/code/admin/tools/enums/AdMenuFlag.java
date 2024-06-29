package com.turing.code.admin.tools.enums;

/**
 * @author Turing
 */
public enum AdMenuFlag {

    MENU("菜单", 1),

    INTERFACE("接口", 2),

    COMM_PAGE("普通页面", -1);

    private String label;

    private Integer value;

    AdMenuFlag(String label, Integer value) {
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
