package com.zhenwu.api.aspect;

import com.zhenwu.api.annotation.WebLog;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.entity.ApiUserOperateLog;
import com.zhenwu.api.model.vo.LoginUserVO;
import com.zhenwu.api.service.ApiUserOperateLogService;
import com.zhenwu.api.util.RedisIdWorker;
import com.zhenwu.api.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

import static com.zhenwu.api.constant.RedisConstants.USER_OPERATE_LOG_KEY;

/**
 * @author zhenwu
 * 用户操作日志切面
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    private static final String USER = "user";

    private static final String REQUEST_METHOD = "requestMethod";

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private ApiUserOperateLogService apiUserOperateLogService;

    @Pointcut("execution(public * com.zhenwu.api.controller.*.*(..))")
    public void controllerCall() {
    }

    @Before("controllerCall()")
    public void before(JoinPoint joinPoint) {
        MDC.put(REQUEST_METHOD, joinPoint.getSignature().getName());
        if (UserHolder.getUser() != null) {
            MDC.put(USER, UserHolder.getUser().getUserAccount());
        }
    }

    @AfterReturning(returning = "req", pointcut = "controllerCall()")
    public void afterReturning(JoinPoint jp, Object req) throws Exception {
        MDC.remove(USER);
    }

    @Around("controllerCall()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1.获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if (!method.isAnnotationPresent(WebLog.class)) {
            return joinPoint.proceed();
        }

        ApiUserOperateLog apiUserOperateLog = new ApiUserOperateLog();
        apiUserOperateLog.setId(this.redisIdWorker.nextId(USER_OPERATE_LOG_KEY));
        LoginUserVO user = UserHolder.getUser();
        if (user != null) {
            apiUserOperateLog.setUserId(user.getId());
        }

        // 2.获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        apiUserOperateLog.setRequestMethod(request.getMethod());
        apiUserOperateLog.setRequestUri(request.getRequestURI().substring(request.getContextPath().length()));
        apiUserOperateLog.setRequestIp(RequestUtil.getRequestIp(request));

        // 3.获取API接口信息
        Operation annotation = method.getAnnotation(Operation.class);
        apiUserOperateLog.setOperationDescription(annotation.summary());

        // 4.获取请求参数
        Object[] args = joinPoint.getArgs();
        apiUserOperateLog.setRequestParameter(RequestUtil.extractParameter(method, args));

        // 5.执行目标方法并记录日志
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            if (e instanceof BasicException) {
                BasicException basicException = (BasicException) e;
                apiUserOperateLog.setResponseStatus(basicException.getCode());
                apiUserOperateLog.setErrorReason(basicException.getMessage());
            } else {
                apiUserOperateLog.setResponseStatus(ErrorCode.SYSTEM_ERROR.getCode());
                apiUserOperateLog.setErrorReason(e.getMessage());
            }
            throw e;
        } finally {
            stopWatch.stop();
            apiUserOperateLog.setCostTime((int) stopWatch.getTotalTimeMillis());
            log.info(apiUserOperateLog.toString());
            this.apiUserOperateLogService.save(apiUserOperateLog);
        }
        return result;
    }
}