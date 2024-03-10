package com.zhenwu.api.mapper;

import com.zhenwu.api.model.dto.user.QueryUserInfoForm;
import com.zhenwu.api.model.entity.ApiUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhenwu.api.model.vo.LoginUserVO;

import java.util.List;
import java.util.Map;

/**
* @author zhenwu
* @description 针对表【api_user(用户表)】的数据库操作Mapper
*/
public interface ApiUserMapper extends BaseMapper<ApiUser> {

    /**
     * 用户登录
     * @param paramMap 用户账户和密码
     * @return 登录用户信息
     */
    LoginUserVO userLogin(Map<String, String> paramMap);

    /**
     * 查询数据表中总的用户数
     * @param queryUserInfoForm 查询条件
     * @return 数据表中总的用户数
     */
    long listUserInfoCount(QueryUserInfoForm queryUserInfoForm);

    /**
     * 分页查询用户信息
     * @param queryUserInfoForm 查询条件
     * @return 用户信息
     */
    List<ApiUser> listUserInfoByPage(QueryUserInfoForm queryUserInfoForm);
}




