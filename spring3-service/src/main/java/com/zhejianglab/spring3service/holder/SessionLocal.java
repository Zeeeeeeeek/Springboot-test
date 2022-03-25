package com.zhejianglab.spring3service.holder;

import com.zhejianglab.spring3dao.vo.UserVo;
import lombok.Data;

@Data
public class SessionLocal {

    private static ThreadLocal<LocalInfo> local = new ThreadLocal<>();

    /**
     * 设置local info
     * @param localInfo
     */
    public static void setLocalInfo(LocalInfo localInfo){
        local.set(localInfo);
    }

    /**
     * 设置用户信息
     * @param userVo
     */
    public static void setUserInfo(UserVo userVo){
        getLocalInfo().setUserVo(userVo);
    }

    /**
     * 获取local info
     * @return
     */
    public static LocalInfo getLocalInfo(){
        return local.get();
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserVo getUserInfo(){
        return getLocalInfo().getUserVo();
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken(){
        return getLocalInfo().getToken();
    }

    /**
     * 删除存储信息
     */
    public static void remove(){
        local.remove();
    }

}
