package com.zhejianglab.spring3service.holder;

import com.zhejianglab.spring3dao.vo.UserVo;
import lombok.Data;

@Data
public class LocalInfo {
    private UserVo userVo;
    private String token;
}
