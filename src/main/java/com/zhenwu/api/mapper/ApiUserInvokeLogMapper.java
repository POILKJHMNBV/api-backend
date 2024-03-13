package com.zhenwu.api.mapper;

import com.zhenwu.api.model.entity.ApiUserInvokeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenwu.api.model.vo.InterfaceAnalysisVo;

import java.util.List;

/**
* @author zhenwu
* @description 针对表【api_user_invoke_log(用户调用接口日志表)】的数据库操作Mapper
*/
public interface ApiUserInvokeLogMapper extends BaseMapper<ApiUserInvokeLog> {
    List<InterfaceAnalysisVo> queryInterfaceInvokeTop5();
}