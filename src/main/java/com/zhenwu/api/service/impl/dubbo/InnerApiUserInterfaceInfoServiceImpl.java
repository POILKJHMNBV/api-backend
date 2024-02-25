package com.zhenwu.api.service.impl.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhenwu.api.mapper.ApiUserInterfaceInfoMapper;
import com.zhenwu.api.model.entity.ApiUserInterfaceInfo;
import com.zhenwu.common.service.InnerApiUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author zhenwu
 */
@DubboService
public class InnerApiUserInterfaceInfoServiceImpl implements InnerApiUserInterfaceInfoService {

    @Resource
    private ApiUserInterfaceInfoMapper apiUserInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        synchronized (String.valueOf(userId).intern()) {
            // 1.判断用户是否调用过该接口
            QueryWrapper<ApiUserInterfaceInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("interface_info_id", interfaceInfoId);
            queryWrapper.eq("user_id", userId);
            ApiUserInterfaceInfo apiUserInterfaceInfo = this.apiUserInterfaceInfoMapper.selectOne(queryWrapper);
            if (apiUserInterfaceInfo == null) {
                // 未调用过，插入调用信息
                ApiUserInterfaceInfo inertApiUserInterfaceInfo = new ApiUserInterfaceInfo();
                inertApiUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
                inertApiUserInterfaceInfo.setUserId(userId);
                inertApiUserInterfaceInfo.setTotalNum(100);
                inertApiUserInterfaceInfo.setLeftNum(99);
                return this.apiUserInterfaceInfoMapper.insert(inertApiUserInterfaceInfo) == 1;
            }

            // 2.调用过
            UpdateWrapper<ApiUserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("interface_info_id", interfaceInfoId);
            updateWrapper.eq("user_id", userId);
            updateWrapper.gt("left_num", 0);
            updateWrapper.setSql("left_num = left_num - 1");
            return this.apiUserInterfaceInfoMapper.update(apiUserInterfaceInfo, updateWrapper) == 1;
        }
    }

    @Override
    public int queryLeftInvokeNum(long interfaceInfoId, long userId) {
        QueryWrapper<ApiUserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interface_info_id", interfaceInfoId);
        queryWrapper.eq("user_id", userId);
        ApiUserInterfaceInfo apiUserInterfaceInfo = this.apiUserInterfaceInfoMapper.selectOne(queryWrapper);
        return apiUserInterfaceInfo == null ? 100 : apiUserInterfaceInfo.getLeftNum();
    }
}