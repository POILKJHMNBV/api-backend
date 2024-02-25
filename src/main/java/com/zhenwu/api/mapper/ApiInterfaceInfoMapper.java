package com.zhenwu.api.mapper;

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
     * 查询 路由信息
     * @return 路由信息
     */
    List<Map<String, String>> queryRouteInfo();
}