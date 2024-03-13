package com.zhenwu.api.controller;

import com.zhenwu.api.annotation.PreAuthorize;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.model.vo.InterfaceAnalysisVo;
import com.zhenwu.api.service.ApiUserInvokeLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenwu
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
@Tag(name = "AnalysisController", description = "分析web接口")
public class AnalysisController {

    @Resource
    private ApiUserInvokeLogService apiUserInvokeLogService;

    @GetMapping("/interface/analysis")
    @PreAuthorize("admin")
    public Result<List<InterfaceAnalysisVo>> interfaceAnalysis() {
        List<InterfaceAnalysisVo> interfaceAnalysisVo = this.apiUserInvokeLogService.queryInterfaceInvokeTop5();
        return Result.success(interfaceAnalysisVo);
    }
}