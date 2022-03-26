package com.zhejianglab.spring3service.holder;

import com.zhejianglab.spring3dao.vo.UserVo;
import lombok.Data;

/**
 * @author chenze
 * @date 2022/3/26
 */
@Data
public class LocalInfo {
    private UserVo userVo;
    private String token;
}
