package com.zhejianglab.spring3common.eunm;


/**
 * @author chenze
 * @date 2022/3/26
 */
public enum RoleEnums {

    UNKNOWN(-1, "不存在该角色"),
    ADMIN(0, "超级管理员"),
    NORMAL(1, "一般人员"),
    SUPPORT(2, "支撑人员");

    private Integer type;
    private String description;

    RoleEnums(Integer type, String description) {
        this.type = type;
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

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

}
