package com.zhenwu.api.aspect;

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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Resource
    private RedisIdWorker redisIdWorker;

    @Resource
    private ApiUserOperateLogService apiUserOperateLogService;

    @Around("execution(public * com.zhenwu.api.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ApiUserOperateLog apiUserOperateLog = new ApiUserOperateLog();
        apiUserOperateLog.setId(this.redisIdWorker.nextId(USER_OPERATE_LOG_KEY));
        LoginUserVO user = UserHolder.getUser();
        if (user != null) {
            apiUserOperateLog.setUserId(user.getId());
        }

        // 1.获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        apiUserOperateLog.setRequestMethod(request.getMethod());
        apiUserOperateLog.setRequestUri(request.getRequestURI().substring(request.getContextPath().length()));
        apiUserOperateLog.setRequestIp(RequestUtil.getRequestIp(request));

        // 2.获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

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