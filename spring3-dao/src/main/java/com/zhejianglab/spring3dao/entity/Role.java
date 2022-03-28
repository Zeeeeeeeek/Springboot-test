package com.zhejianglab.spring3dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@Getter
@Setter
@TableName("role")
@Schema(name = "Role对象", description = "角色信息表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "角色类型")
    @TableField("role_type")
    private Integer roleType;

    @Schema(name = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(name = "角色功能")
    @TableField("role_function")
    private String roleFunction;

    @Schema(name = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
