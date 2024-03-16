package com.zhenwu.api.constant;

/**
 * @author zhenwu
 */
public interface RedisConstants {
    /**
     * 登录用户token前缀
     */
    String LOGIN_USER_KEY = "login:token:";

    /**
     * 登录用户默认token有效时间，单位：秒
     */
    Long LOGIN_USER_TTL = 1800L;

    /**
     * 路由信息前缀
     */
    String ROUTE = "route";

    /**
     * 在线用户key
     */
    String LOGIN_USER_ACCOUNT = "login:online:";

    /**
     * 用户操作日志key
     */
    String USER_OPERATE_LOG_KEY = "userOperateLog";

    /**
     * 用户调用接口日志key
     */
    String USER_INVOKE_LOG_KEY = "userInvokeLog";

    /**
     * 登录验证码前缀
     */
    String LOGIN_VERIFICATION_CODE_KEY = "login:verificationCode:";

    /**
     * 注册验证码前缀
     */
    String REGISTER_VERIFICATION_CODE_KEY = "register:verificationCode:";

    /**
     * 用户验证码默认有效时间，单位：秒
     */
    Long VERIFICATION_CODE_TTL = 60L;

    /**
     * 黑名单IP Key
     */
    String BLACKLIST_IP = "blacklistIp";

    /**
     * 黑名单 IP 禁用时间
     */
    Long BLACKLIST_IP_PROHIBIT_TTL = 36000L;
}