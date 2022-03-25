package com.zhejianglab.spring3dao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author chenze
 * @since 2022-03-24
 */
@Data
public class UserDTO{

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String password;

}
