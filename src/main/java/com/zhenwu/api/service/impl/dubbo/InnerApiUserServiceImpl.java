package com.zhenwu.api.service.impl.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhenwu.api.mapper.ApiUserMapper;
import com.zhenwu.api.model.entity.ApiUser;
import com.zhenwu.common.entity.InnerApiTransferUser;
import com.zhenwu.common.service.InnerApiUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * @author zhenwu
 */
@DubboService
public class InnerApiUserServiceImpl implements InnerApiUserService {

    @Resource
    private ApiUserMapper apiUserMapper;

    @Override
    public InnerApiTransferUser getUserInfo(String userAccesskey) {
        QueryWrapper<ApiUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_accessKey", userAccesskey);
        ApiUser apiUser = this.apiUserMapper.selectOne(queryWrapper);
        if (apiUser == null) {
            return null;
        }
        InnerApiTransferUser innerApiTransferUser = new InnerApiTransferUser();
        BeanUtils.copyProperties(apiUser, innerApiTransferUser);
        return innerApiTransferUser;
    }
}