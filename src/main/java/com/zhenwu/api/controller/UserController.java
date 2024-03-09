package com.zhenwu.api.controller;

import com.zhenwu.api.annotation.WebLog;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.model.dto.user.UserLoginForm;
import com.zhenwu.api.model.dto.user.UserRegisterForm;
import com.zhenwu.api.model.vo.LoginUserVO;
import com.zhenwu.api.service.ApiUserService;
import com.zhenwu.api.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zhenwu
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "UserController", description = "用户web接口")
public class UserController {

    @Resource
    private ApiUserService apiUserService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    @WebLog
    public Result<Long> userRegister(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        return Result.success(this.apiUserService.userRegister(userRegisterForm.getUserAccount(),
                userRegisterForm.getUserPassword(),
                userRegisterForm.getCheckPassword()));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @WebLog
    public Result<String> userLogin(@RequestBody @Valid UserLoginForm userLoginForm, HttpServletRequest request) {
        return Result.success(this.apiUserService.userLogin(userLoginForm.getUserAccount(),
                userLoginForm.getUserPassword(),
                RequestUtil.getRequestIp(request)));
    }

    @GetMapping("/loadUserInfo")
    @Operation(summary = "加载登录用户信息")
    public Result<LoginUserVO> loadUserInfo() {
        return Result.success(UserHolder.getUser());
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录")
    public Result<?> logout(HttpServletRequest request) {
        this.apiUserService.logout(request.getHeader("Authorization"));
        return Result.success();
    }
}