package com.zhenwu.api.mapper;

import com.zhenwu.api.model.dto.interfaceinfo.QueryInterfaceInfoForm;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Mapper
*/
public interface ApiInterfaceInfoMapper extends BaseMapper<ApiInterfaceInfo> {

    /**
     * 分页查询接口信息
     * @param queryInterfaceInfoForm 查询条件
     * @return 接口信息
     */
    long listInterfaceInfoCount(QueryInterfaceInfoForm queryInterfaceInfoForm);

    /**
     * 分页查询接口信息
     * @param queryInterfaceInfoForm 查询条件
     * @return 接口信息
     */
    List<ApiInterfaceInfo> listInterfaceInfoByPage(QueryInterfaceInfoForm queryInterfaceInfoForm);

    /**
     * 查询 路由信息
     * @return 路由信息
     */
    List<Map<String, String>> queryRouteInfo();

    /**
     * 批量删除接口信息
     * @param ids 接口id数组
     * @return 成功删除的数据条数
     */
    int deleteInterfaceInfoByIds(Long[] ids);
}