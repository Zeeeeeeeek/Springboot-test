package com.zhejianglab.spring3common.eunm;


/**
 * @author chenze
 * @date 2022/3/26
 */
public enum RoleEnums {

    UNKNOWN(-1, "UNKNOWN", "不存在该角色"),
    ADMIN(0, "ADMIN", "超级管理员"),
    NORMAL(1, "NORMAL", "一般人员"),
    SUPPORT(2, "SUPPORT", "支撑人员");

    private final Integer type;
    private final String value;
    private final String description;

    RoleEnums(Integer type, String value, String description) {
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public static RoleEnums parse(Integer type) {
        RoleEnums[] values = RoleEnums.values();
        for (RoleEnums enums : values) {
            if (enums.getType().equals(type)) {
                return enums;
            }
        }
        return RoleEnums.UNKNOWN;
    }

    public static RoleEnums parseByValue(String value) {
        RoleEnums[] values = RoleEnums.values();
        for (RoleEnums enums : values) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        return RoleEnums.UNKNOWN;
    }

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getValue() {
        return this.value;
    }

}
