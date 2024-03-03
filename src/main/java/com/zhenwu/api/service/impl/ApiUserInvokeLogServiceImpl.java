package com.zhenwu.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.model.entity.ApiUserInvokeLog;
import com.zhenwu.api.service.ApiUserInvokeLogService;
import com.zhenwu.api.mapper.ApiUserInvokeLogMapper;
import org.springframework.stereotype.Service;

/**
* @author zhenwu
* @description 针对表【api_user_invoke_log(用户调用接口日志表)】的数据库操作Service实现
*/
@Service
public class ApiUserInvokeLogServiceImpl extends ServiceImpl<ApiUserInvokeLogMapper, ApiUserInvokeLog>
    implements ApiUserInvokeLogService {

}