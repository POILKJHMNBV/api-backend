package com.zhenwu.api.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.model.vo.LoginUserVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

import static com.zhenwu.api.constant.RedisConstants.LOGIN_USER_KEY;
import static com.zhenwu.api.constant.RedisConstants.LOGIN_USER_TTL;

/**
 * @author zhenwu
 * token刷新拦截器
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取token
        String token = request.getHeader("Authorization");

        if (StrUtil.isBlank(token)) {
            // token未上传
            return true;
        }

        // 2.token上传，获取用户信息
        String key = LOGIN_USER_KEY + token;
        String userStr = this.stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(userStr)) {
            // 用户不存在或token过期
            return true;
        }

        // 3.获取用户信息，将用户信息存入UserHolder
        LoginUserVO loginUser = JSONUtil.toBean(userStr, LoginUserVO.class);
        UserHolder.saveUser(loginUser);

        // 4.刷新token
        this.stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.SECONDS);
        return true;
    }
}
