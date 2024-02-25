package com.zhenwu.api.aspect;

import com.zhenwu.api.annotation.PreAuthorize;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.exception.AccessDeniedException;
import com.zhenwu.api.model.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhenwu
 * 权限校验切面
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {

    @Before("execution(public * com.zhenwu.api.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        // 获取方法信息，判断用户是否具有相应权限
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(PreAuthorize.class)) {
            String value = method.getAnnotation(PreAuthorize.class).value();
            if (!RoleEnum.ADMIN.getRoleName().equals(value)) {
                throw new AccessDeniedException(ErrorCode.FORBIDDEN_ERROR);
            }
        }
    }
}
