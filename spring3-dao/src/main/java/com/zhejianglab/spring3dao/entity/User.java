package com.zhejianglab.spring3dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@Getter
@Setter
@Builder
@TableName("user")
@Schema(name = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "用户名")
    @TableField("user_name")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @Schema(name = "用户真实姓名")
    @TableField("real_name")
    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "角色类型：0：超级管理员；1：一般人员")
    @TableField("role_type")
    private Integer roleType;

    @Schema(name = "0:锁定；1:有效；2：失效")
    @TableField("status")
    private Integer status;

    @Schema(name = "用户密码")
    @TableField("password")
    @Length(max = 10, message = "密码长度不能超过10")
    private String password;

    @Schema(name = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
