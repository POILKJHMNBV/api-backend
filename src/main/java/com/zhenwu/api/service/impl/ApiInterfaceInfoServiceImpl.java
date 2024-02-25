package com.zhenwu.api.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.constant.RedisConstants;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.mapper.ApiInterfaceInfoMapper;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.api.service.ApiInterfaceInfoService;
import com.zhenwu.common.entity.InterfaceRoute;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Service实现
*/
@Service
public class ApiInterfaceInfoServiceImpl extends ServiceImpl<ApiInterfaceInfoMapper, ApiInterfaceInfo>
    implements ApiInterfaceInfoService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ApiInterfaceInfoMapper apiInterfaceInfoMapper;

    @Override
    public synchronized boolean addApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo) {
        // 1.判断接口信息是否存在
        String interfaceHost = apiInterfaceInfo.getInterfaceHost();
        String interfacePath = apiInterfaceInfo.getInterfacePath();
        Long count = this.query().eq("interface_vendor", apiInterfaceInfo.getInterfaceVendor())
                .eq("interface_vendor_name", apiInterfaceInfo.getInterfaceVendorName())
                .eq("interface_host", interfaceHost)
                .eq("interface_path", interfacePath)
                .eq("interface_request_method", apiInterfaceInfo.getInterfaceRequestMethod())
                .count();
        if (count > 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "接口信息已经已经存在");
        }

        // 2.接口信息不存在，添加接口信息
        apiInterfaceInfo.setInterfacePublishUserid(UserHolder.getUser().getId());
        apiInterfaceInfo.setInterfaceToken(UUID.randomUUID().toString(true));
        boolean result = this.save(apiInterfaceInfo);

        // 3.更新路由信息
        if (result) {
            try {
                this.loadRouteInfo();
            } catch (Exception e) {
                log.error("路由信息更新失败!{}", e);
            }
        }

        // 4.通知网关更新路由信息
        HttpResponse response = HttpRequest.get("http://localhost:10010/gateway/updateRouteInfo").execute();
        return result && response.isOk();
    }

    @Override
    public boolean updateApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo) {
        return false;
    }

    @Override
    public void loadRouteInfo() {
        List<Map<String, String>> routeList = this.apiInterfaceInfoMapper.queryRouteInfo();
        if (routeList.isEmpty()) {
            return;
        }
        List<InterfaceRoute> interfaceRouteList = new ArrayList<>();
        routeList.forEach(route -> {
            InterfaceRoute interfaceRoute = new InterfaceRoute();
            interfaceRoute.setHost(route.get("host"));
            List<String> paths = Arrays.asList(route.get("paths").split(","));
            interfaceRoute.setPaths(paths);
            interfaceRouteList.add(interfaceRoute);
        });
        this.stringRedisTemplate.opsForValue().set(RedisConstants.ROUTE, JSONUtil.toJsonStr(interfaceRouteList));
    }
}