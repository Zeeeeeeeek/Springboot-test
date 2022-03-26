package com.zhejianglab.spring3dao.vo;

import lombok.Data;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Data
public class UserVo {
    private Long id;
    private String userName;
    private String realName;
    private Integer roleType;
}
