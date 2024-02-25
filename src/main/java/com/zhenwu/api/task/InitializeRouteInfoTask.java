package com.zhenwu.api.task;

import com.zhenwu.api.service.ApiInterfaceInfoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhenwu
 * 项目启动后初始化路由信息
 */
@Component
public class InitializeRouteInfoTask implements CommandLineRunner {

    @Resource
    private ApiInterfaceInfoService apiInterfaceInfoService;

    @Override
    public void run(String... args) throws Exception {
        this.apiInterfaceInfoService.loadRouteInfo();
    }
}