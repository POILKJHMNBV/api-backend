package com.zhenwu.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.constant.RedisConstants;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.mapper.ApiInterfaceInfoMapper;
import com.zhenwu.api.model.dto.interfaceinfo.QueryInterfaceInfoForm;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.api.model.enums.RoleEnum;
import com.zhenwu.api.service.ApiInterfaceInfoService;
import com.zhenwu.common.entity.InterfaceRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Service实现
*/
@Service
@Slf4j
public class ApiInterfaceInfoServiceImpl extends ServiceImpl<ApiInterfaceInfoMapper, ApiInterfaceInfo>
    implements ApiInterfaceInfoService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ApiInterfaceInfoMapper apiInterfaceInfoMapper;

    @Override
    public Page<ApiInterfaceInfo> listInterfaceInfoByPage(QueryInterfaceInfoForm queryInterfaceInfoForm) {
        queryInterfaceInfoForm.setStart();
        if (RoleEnum.CUSTOMER.getRoleName().equals(UserHolder.getUser().getUserRole())) {
            queryInterfaceInfoForm.setInterfaceStatus(1);
        }
        long count = this.apiInterfaceInfoMapper.listInterfaceInfoCount(queryInterfaceInfoForm);
        List<ApiInterfaceInfo> interfaceInfoList = this.apiInterfaceInfoMapper.listInterfaceInfoByPage(queryInterfaceInfoForm);
        long current = queryInterfaceInfoForm.getCurrent();
        long size = queryInterfaceInfoForm.getPageSize();
        Page<ApiInterfaceInfo> page = new Page<>(current, size);
        page.setRecords(interfaceInfoList);
        page.setTotal(count);

        page.getRecords().forEach(this::filterSensitiveInformation);
        return page;
    }

    private void filterSensitiveInformation(ApiInterfaceInfo apiInterfaceInfo) {
        Date createTime = apiInterfaceInfo.getCreateTime();
        apiInterfaceInfo.setCreateTime(DateUtil.offset(createTime, DateField.HOUR_OF_DAY, -8));
        Date updateTime = apiInterfaceInfo.getUpdateTime();
        apiInterfaceInfo.setUpdateTime(DateUtil.offset(updateTime, DateField.HOUR_OF_DAY, -8));
        if (RoleEnum.ADMIN.getRoleName().equals(UserHolder.getUser().getUserRole())) {
            String interfaceVendor = apiInterfaceInfo.getInterfaceVendor();
            apiInterfaceInfo.setInterfacePath(apiInterfaceInfo.getInterfacePath().substring(interfaceVendor.length() + 1));
        }
        if (RoleEnum.CUSTOMER.getRoleName().equals(UserHolder.getUser().getUserRole())) {
            apiInterfaceInfo.setInterfaceVendor(null);
            apiInterfaceInfo.setInterfaceVendorName(null);
            apiInterfaceInfo.setInterfaceHost(null);
            apiInterfaceInfo.setInterfaceRequestParamsMime(null);
            apiInterfaceInfo.setInterfaceRequestParamsCharset(null);
            apiInterfaceInfo.setInterfaceRequestHeader(null);
            apiInterfaceInfo.setInterfaceResponseHeader(null);
            apiInterfaceInfo.setInterfaceStatus(null);
            apiInterfaceInfo.setInterfaceRequestMethod(null);
            apiInterfaceInfo.setInterfacePublishUserid(null);
            apiInterfaceInfo.setInterfaceDelete(null);
            apiInterfaceInfo.setCreateTime(null);
            apiInterfaceInfo.setUpdateTime(null);

        }
    }

    @Override
    public ApiInterfaceInfo queryInterfaceInfoById(long id) {
        ApiInterfaceInfo apiInterfaceInfo = this.getById(id);
        this.filterSensitiveInformation(apiInterfaceInfo);
        return apiInterfaceInfo;
    }

    @Override
    public synchronized boolean addApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo) {
        // 1.判断接口信息是否存在
        long count = queryInterfaceInfoCount(apiInterfaceInfo);
        if (count > 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "接口信息已经已经存在");
        }

        // 2.接口信息不存在，添加接口信息
        apiInterfaceInfo.setInterfacePublishUserid(UserHolder.getUser().getId());
        apiInterfaceInfo.setInterfaceToken(UUID.randomUUID().toString(true));
        apiInterfaceInfo.setInterfacePath("/" + apiInterfaceInfo.getInterfaceVendor() + apiInterfaceInfo.getInterfacePath());
        boolean result = this.save(apiInterfaceInfo);

        // 3.更新路由信息
        if (result) {
            try {
                this.loadRouteInfo();
            } catch (Exception e) {
                log.error("路由信息更新失败: ", e);
            }
        }

        // TODO 4.通知网关更新路由信息(优化)
        try {
            HttpResponse response = HttpRequest.get("http://localhost:10010/gateway/updateRouteInfo").execute();

            if (!response.isOk()) {
                log.error("路由信息更新失败");
            }
        } catch (Exception e) {
            log.error("路由信息更新失败: ", e);
        }
        return result;
    }

    private long queryInterfaceInfoCount(ApiInterfaceInfo apiInterfaceInfo) {
        Long id = apiInterfaceInfo.getId();
        return id == null ? this.query().eq("interface_vendor", apiInterfaceInfo.getInterfaceVendor())
                .eq("interface_vendor_name", apiInterfaceInfo.getInterfaceVendorName())
                .eq("interface_host", apiInterfaceInfo.getInterfaceHost())
                .eq("interface_path", apiInterfaceInfo.getInterfacePath())
                .eq("interface_request_method", apiInterfaceInfo.getInterfaceRequestMethod())
                .eq("interface_delete", 1)
                .count()
                : this.query().eq("id", id)
                              .eq("interface_delete", 1).count();
    }

    @Override
    public synchronized boolean updateApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo) {
        // 判断接口信息是否存在
        long count = queryInterfaceInfoCount(apiInterfaceInfo);
        if (count == 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "接口信息不存在");
        }
        return this.updateById(apiInterfaceInfo);
    }

    @Override
    public void loadRouteInfo() {
        List<Map<String, String>> routeList = this.apiInterfaceInfoMapper.queryRouteInfo();
        if (routeList.isEmpty()) {
            this.stringRedisTemplate.delete(RedisConstants.ROUTE);
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

    @Override
    public int deleteApiInterfaceInfo(Long[] ids) {
        return this.apiInterfaceInfoMapper.deleteInterfaceInfoByIds(ids);
    }

    @Override
    public boolean onlineInterfaceInfo(long id) {
        ApiInterfaceInfo apiInterfaceInfo = new ApiInterfaceInfo();
        apiInterfaceInfo.setId(id);
        long count = this.queryInterfaceInfoCount(apiInterfaceInfo);
        if (count == 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "接口信息不存在");
        }
        return this.update().set("interface_status", 1).eq("id", id).update();
    }

    @Override
    public boolean offlineInterfaceInfo(long id) {
        ApiInterfaceInfo apiInterfaceInfo = new ApiInterfaceInfo();
        apiInterfaceInfo.setId(id);
        long count = this.queryInterfaceInfoCount(apiInterfaceInfo);
        if (count == 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "接口信息不存在");
        }
        return this.update().set("interface_status", 0).eq("id", id).update();
    }
}