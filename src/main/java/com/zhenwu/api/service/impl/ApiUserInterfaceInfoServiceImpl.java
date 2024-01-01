package com.zhenwu.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.model.entity.ApiUserInterfaceInfo;
import com.zhenwu.api.service.ApiUserInterfaceInfoService;
import com.zhenwu.api.mapper.ApiUserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author zhenwu
* @description 针对表【api_user_interface_info(用户调用接口关系表)】的数据库操作Service实现
*/
@Service
public class ApiUserInterfaceInfoServiceImpl extends ServiceImpl<ApiUserInterfaceInfoMapper, ApiUserInterfaceInfo>
    implements ApiUserInterfaceInfoService{

}




