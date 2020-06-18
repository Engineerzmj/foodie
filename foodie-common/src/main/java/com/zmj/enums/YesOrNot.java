package com.zmj.enums;

/**
 * 是否 枚举
 */
public enum YesOrNot {

    NOT(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNot(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
