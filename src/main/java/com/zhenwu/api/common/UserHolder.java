package com.zhenwu.api.common;

import com.zhenwu.api.model.vo.LoginUserVO;

/**
 * @author zhenwu
 * 利用ThreadLocal保存当前用户信息
 */
public final class UserHolder {

    private UserHolder(){}

    private static final ThreadLocal<LoginUserVO> threadLocal = new ThreadLocal<>();

    public static void saveUser(LoginUserVO apiUser) {
        threadLocal.set(apiUser);
    }

    public static LoginUserVO getUser(){
        return threadLocal.get();
    }

    public static void removeUser(){
        threadLocal.remove();
    }
}