package com.zhenwu.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenwu.api.model.dto.interfaceinfo.QueryInterfaceInfoForm;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Service
*/
public interface ApiInterfaceInfoService extends IService<ApiInterfaceInfo> {

    /**
     * 分页查询接口信息
     * @param queryInterfaceInfoForm 查询条件
     * @return 该页的接口信息
     */
    Page<ApiInterfaceInfo> listInterfaceInfoByPage(QueryInterfaceInfoForm queryInterfaceInfoForm);

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

    /**
     * 删除接口信息
     * @param ids 接口id
     * @return 接口信息是否删除成功
     */
    int deleteApiInterfaceInfo(Long[] ids);

    /**
     * 接口上线
     * @param id 接口id
     * @return 接口是否上线成功
     */
    boolean onlineInterfaceInfo(long id);

    /**
     * 接口下线
     * @param id 接口id
     * @return 接口是否下线成功
     */
    boolean offlineInterfaceInfo(long id);
}
