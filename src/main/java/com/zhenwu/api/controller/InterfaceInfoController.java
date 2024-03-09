package com.zhenwu.api.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenwu.api.annotation.PreAuthorize;
import com.zhenwu.api.annotation.WebLog;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.dto.interfaceinfo.*;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
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
        return Result.success(this.apiInterfaceInfoService.listInterfaceInfoByPage(queryInterfaceInfoForm));
    }

    @PostMapping("/add")
    @Operation(summary = "添加接口信息")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> addInterfaceInfo(@RequestBody @Valid AddInterfaceInfoForm addInterfaceInfoForm) {
        ApiInterfaceInfo interfaceInfo = new ApiInterfaceInfo();
        BeanUtils.copyProperties(addInterfaceInfoForm, interfaceInfo);
        boolean result = this.apiInterfaceInfoService.addApiInterfaceInfo(interfaceInfo);
        return Result.success(result ? 1L : 0L);
    }

    @PutMapping("/update")
    @Operation(summary = "更新接口信息")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> updateInterfaceInfo(@RequestBody @Valid UpdateInterfaceInfoForm updateInterfaceInfoForm) {
        ApiInterfaceInfo interfaceInfo = new ApiInterfaceInfo();
        BeanUtils.copyProperties(updateInterfaceInfoForm, interfaceInfo);
        boolean result = this.apiInterfaceInfoService.updateApiInterfaceInfo(interfaceInfo);
        return Result.success(result ? 1L : 0L);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除接口信息")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> deleteInterfaceInfo(@RequestBody DeleteInterfaceInfoForm deleteInterfaceInfoForm) {
        if (deleteInterfaceInfoForm == null || deleteInterfaceInfoForm.getIds() == null) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        Long[] ids = deleteInterfaceInfoForm.getIds();
        for (long id : ids) {
            if (id < 0) {
                throw new BasicException(ErrorCode.PARAMS_ERROR);
            }
        }
        int result = this.apiInterfaceInfoService.deleteApiInterfaceInfo(ids);
        return Result.success(result == ids.length ? ids.length : 0L);
    }

    /**
     * 接口上线
     * @return 接口上线结果
     */
    @PutMapping("/online/{id}")
    @Operation(summary = "接口上线")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> onlineInterfaceInfo(@PathVariable("id") Long id) {
        if (id == null || id < 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = this.apiInterfaceInfoService.onlineInterfaceInfo(id);
        return Result.success(result ? 1L : 0L);
    }

    /**
     * 接口下线
     * @return 接口下线结果
     */
    @PutMapping("/offline/{id}")
    @Operation(summary = "接口下线")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> offlineInterfaceInfo(@PathVariable("id") Long id) {
        if (id == null || id < 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = this.apiInterfaceInfoService.offlineInterfaceInfo(id);
        return Result.success(result ? 1L : 0L);
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
    @WebLog
    public Result<Object> invokeInterface(@RequestBody InvokeInterfaceForm invokeInterfaceForm) {
        if (invokeInterfaceForm == null || invokeInterfaceForm.getId() == null) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
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
