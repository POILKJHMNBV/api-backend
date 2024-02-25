package com.zhenwu.api.service.impl.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhenwu.api.mapper.ApiInterfaceInfoMapper;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.common.entity.InnerApiTransferInterfaceInfo;
import com.zhenwu.common.service.InnerApiInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * @author zhenwu
 */
@DubboService
public class InnerApiInterfaceInfoServiceImpl implements InnerApiInterfaceInfoService {

    @Resource
    private ApiInterfaceInfoMapper apiInterfaceInfoMapper;

    @Override
    public InnerApiTransferInterfaceInfo getInterfaceInfo(String interfaceToken) {
        QueryWrapper<ApiInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interface_token", interfaceToken);
        ApiInterfaceInfo apiInterfaceInfo = this.apiInterfaceInfoMapper.selectOne(queryWrapper);
        if (apiInterfaceInfo == null) {
            return null;
        }
        InnerApiTransferInterfaceInfo innerApiTransferInterfaceInfo = new InnerApiTransferInterfaceInfo();
        BeanUtils.copyProperties(apiInterfaceInfo, innerApiTransferInterfaceInfo);
        return innerApiTransferInterfaceInfo;
    }
}