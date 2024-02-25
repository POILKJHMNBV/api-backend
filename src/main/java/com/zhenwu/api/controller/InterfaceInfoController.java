package com.zhenwu.api.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenwu.api.annotation.PreAuthorize;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.dto.interfaceinfo.AddInterfaceInfoForm;
import com.zhenwu.api.model.dto.interfaceinfo.InvokeInterfaceForm;
import com.zhenwu.api.model.dto.interfaceinfo.QueryInterfaceInfoForm;
import com.zhenwu.api.model.dto.interfaceinfo.UpdateInterfaceInfoForm;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.api.model.enums.RoleEnum;
import com.zhenwu.api.model.vo.LoginUserVO;
import com.zhenwu.api.service.ApiInterfaceInfoService;
import com.zhenwu.sdk.client.ApiClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhenwu
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
@Tag(name = "InterfaceInfoController", description = "接口信息web接口")
public class InterfaceInfoController {

    @Resource
    private ApiInterfaceInfoService apiInterfaceInfoService;

    @PostMapping("/list/page")
    @Operation(summary = "分页查询接口信息")
    public Result<Page<ApiInterfaceInfo>> listInterfaceInfoByPage(@RequestBody @Valid QueryInterfaceInfoForm queryInterfaceInfoForm) {
        long current = queryInterfaceInfoForm.getCurrent();
        long size = queryInterfaceInfoForm.getPageSize();
        Page<ApiInterfaceInfo> page = this.apiInterfaceInfoService.page(new Page<>(current, size), new QueryWrapper<>());
        page.getRecords().forEach(apiInterfaceInfo -> {
            Date createTime = apiInterfaceInfo.getCreateTime();
            apiInterfaceInfo.setCreateTime(DateUtil.offset(createTime, DateField.HOUR_OF_DAY, -8));
            Date updateTime = apiInterfaceInfo.getUpdateTime();
            apiInterfaceInfo.setUpdateTime(DateUtil.offset(updateTime, DateField.HOUR_OF_DAY, -8));
            if (RoleEnum.CUSTOMER.getRoleName().equals(UserHolder.getUser().getUserRole())) {
                apiInterfaceInfo.setInterfaceVendor(null);
                apiInterfaceInfo.setInterfaceVendorName(null);
                apiInterfaceInfo.setInterfaceHost(null);
                apiInterfaceInfo.setInterfaceRequestParamsMime(null);
                apiInterfaceInfo.setInterfaceRequestParamsCharset(null);
                apiInterfaceInfo.setInterfaceRequestMethod(null);
                apiInterfaceInfo.setInterfacePublishUserid(null);
                apiInterfaceInfo.setCreateTime(null);
                apiInterfaceInfo.setUpdateTime(null);
            }
        });
        return Result.success(page);
    }

    @PostMapping("/add")
    @Operation(summary = "添加接口信息")
    @PreAuthorize("admin")
    public Result<Long> addInterfaceInfo(@RequestBody @Valid AddInterfaceInfoForm addInterfaceInfoForm) {
        ApiInterfaceInfo interfaceInfo = new ApiInterfaceInfo();
        BeanUtils.copyProperties(addInterfaceInfoForm, interfaceInfo);
        boolean result = this.apiInterfaceInfoService.addApiInterfaceInfo(interfaceInfo);
        return Result.success(1L);
    }

    @PutMapping("/update")
    @Operation(summary = "更新接口信息")
    @PreAuthorize("admin")
    public Result<Long> updateInterfaceInfo(@RequestBody @Valid UpdateInterfaceInfoForm updateInterfaceInfoForm) {
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除接口信息")
    @PreAuthorize("admin")
    public Result<Long> deleteInterfaceInfo(@RequestParam("ids") List<Long> ids) {
        return Result.success();
    }

    /**
     * 接口上线
     * @return 接口上线结果
     */
    @PutMapping("/online/{id}")
    @Operation(summary = "接口上线")
    @PreAuthorize("admin")
    public Result<Long> onlineInterfaceInfo(@PathVariable("id") Long id) {
        // TODO
        // 1.校验参数

        // 2.判断接口是否存在

        // 3.判断接口是否可以调用
        return Result.success();
    }

    /**
     * 接口下线
     * @return 接口下线结果
     */
    @PutMapping("/offline/{id}")
    @Operation(summary = "接口下线")
    @PreAuthorize("admin")
    public Result<Long> offlineInterfaceInfo(@PathVariable("id") Long id) {
        // TODO
        return Result.success();
    }


    @GetMapping("/info/{id}")
    @Operation(summary = "查询接口详情信息")
    public Result<ApiInterfaceInfo> queryInterfaceInfoById(@PathVariable("id") Long id) {
        if (id == null || id < 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(this.apiInterfaceInfoService.getById(id));
    }

    @PostMapping("/invoke")
    @Operation(summary = "测试调用接口")
    public Result<Object> invokeInterface(@RequestBody InvokeInterfaceForm invokeInterfaceForm) {
        // 判断接口是否存在
        ApiInterfaceInfo apiInterfaceInfo = this.apiInterfaceInfoService.getById(invokeInterfaceForm.getId());
        if (apiInterfaceInfo == null) {
            throw new BasicException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (0 == apiInterfaceInfo.getInterfaceStatus() || 0 == apiInterfaceInfo.getInterfaceDelete()) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO currentUser = UserHolder.getUser();
        ApiClient apiClient = new ApiClient(currentUser.getUserAccesskey(), currentUser.getUserSecretkey(), currentUser.getUserPublickey());
        String result = apiClient.invoke(apiInterfaceInfo.getInterfaceToken(),
                apiInterfaceInfo.getInterfacePath(),
                JSONUtil.toBean(invokeInterfaceForm.getUserRequestParam(), Map.class));
        return Result.success(result);
    }
}
