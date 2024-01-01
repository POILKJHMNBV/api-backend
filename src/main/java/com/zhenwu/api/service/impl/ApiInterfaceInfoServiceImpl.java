package com.zhenwu.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.api.service.ApiInterfaceInfoService;
import com.zhenwu.api.mapper.ApiInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author zhenwu
* @description 针对表【api_interface_info(接口信息表)】的数据库操作Service实现
*/
@Service
public class ApiInterfaceInfoServiceImpl extends ServiceImpl<ApiInterfaceInfoMapper, ApiInterfaceInfo>
    implements ApiInterfaceInfoService{

}




