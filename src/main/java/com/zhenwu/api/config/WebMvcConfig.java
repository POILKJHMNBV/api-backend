package com.zhenwu.api.config;

import com.zhenwu.api.interceptor.LoginInterceptor;
import com.zhenwu.api.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zhenwu
 * 跨域配置、拦截器配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v3/api-docs",
                "/favicon.ico",
                "/error",
                "/user/register",
                "/user/login",
                "/user/getVerificationCode"
        ).order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(this.stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
