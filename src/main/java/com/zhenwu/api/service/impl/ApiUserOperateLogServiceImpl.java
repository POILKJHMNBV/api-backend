package com.zhenwu.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.model.entity.ApiUserOperateLog;
import com.zhenwu.api.service.ApiUserOperateLogService;
import com.zhenwu.api.mapper.ApiUserOperateLogMapper;
import org.springframework.stereotype.Service;

/**
* @author zhenwu
* @description 针对表【api_user_operate_log(用户操作日志表)】的数据库操作Service实现
*/
@Service
public class ApiUserOperateLogServiceImpl extends ServiceImpl<ApiUserOperateLogMapper, ApiUserOperateLog>
    implements ApiUserOperateLogService {

}