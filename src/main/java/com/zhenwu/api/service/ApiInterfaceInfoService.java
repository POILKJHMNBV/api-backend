package com.zhenwu.api.service;

import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Service
*/
public interface ApiInterfaceInfoService extends IService<ApiInterfaceInfo> {

    /**
     * 添加接口信息
     * @param apiInterfaceInfo 待添加的接口信息
     * @return 接口信息是否添加成功
     */
    boolean addApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo);

    /**
     * 更新接口信息
     * @param apiInterfaceInfo 待更新的接口信息
     * @return 接口信息是否更新成功
     */
    boolean updateApiInterfaceInfo(ApiInterfaceInfo apiInterfaceInfo);

    /**
     * 将路由信息加载至Redis
     */
    void loadRouteInfo();
}
