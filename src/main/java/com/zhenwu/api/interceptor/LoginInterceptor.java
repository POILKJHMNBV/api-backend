package com.zhenwu.api.interceptor;

import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.vo.LoginUserVO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zhenwu
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        LoginUserVO loginUser = UserHolder.getUser();
        if (loginUser == null) {
            // 用户未登录
            throw new BasicException(ErrorCode.UNAUTHORIZED);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}