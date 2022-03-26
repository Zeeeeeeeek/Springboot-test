package com.zhejianglab.spring3dao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author chenze
 * @date 2022/3/26
 */
@Data
public class UserDTO {

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String password;

}
