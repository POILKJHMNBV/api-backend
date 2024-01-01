package com.zhenwu.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.dto.interfaceinfo.AddInterfaceInfoForm;
import com.zhenwu.api.model.dto.interfaceinfo.QueryInterfaceInfoForm;
import com.zhenwu.api.model.dto.interfaceinfo.UpdateInterfaceInfoForm;
import com.zhenwu.api.model.entity.ApiInterfaceInfo;
import com.zhenwu.api.service.ApiInterfaceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
        return Result.success(page);
    }

    @PostMapping("/add")
    @Operation(summary = "添加接口信息")
    public Result<Long> addInterfaceInfo(@RequestBody @Valid AddInterfaceInfoForm addInterfaceInfoForm) {
        ApiInterfaceInfo interfaceInfo = new ApiInterfaceInfo();
        interfaceInfo.setInterfacePublishUserid(1L);
        BeanUtils.copyProperties(addInterfaceInfoForm, interfaceInfo);
        this.apiInterfaceInfoService.save(interfaceInfo);
        return Result.success(1L);
    }

    @PutMapping("/update")
    @Operation(summary = "更新接口信息")
    public Result<Long> updateInterfaceInfo(@RequestBody @Valid UpdateInterfaceInfoForm updateInterfaceInfoForm) {
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除接口信息")
    public Result<Long> deleteInterfaceInfo(@RequestParam("ids") List<Long> ids) {
        return Result.success();
    }

    /**
     * 接口上线
     * @return 接口上线结果
     */
    @PutMapping("/online/{id}")
    @Operation(summary = "接口上线")
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
}
