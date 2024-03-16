package com.zhenwu.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenwu.api.annotation.PreAuthorize;
import com.zhenwu.api.annotation.WebLog;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.dto.user.*;
import com.zhenwu.api.model.entity.ApiUser;
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

    @GetMapping("/getVerificationCode")
    @Operation(summary = "获取验证码")
    public Result<String> getVerificationCode(@RequestParam("userAccount") String userAccount,
                                              @RequestParam("operate") Integer operate) {
        String userAccountPattern = "^[a-zA-Z0-9]{2,20}$";
        if (!userAccount.matches(userAccountPattern)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "用户名内容不正确");
        }
        if (operate == null || (operate != 0 && operate != 1)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(this.apiUserService.generateVerificationCode(userAccount, operate));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    @WebLog
    public Result<Long> userRegister(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        return Result.success(this.apiUserService.userRegister(userRegisterForm.getVerificationCode(),
                userRegisterForm.getUserAccount(),
                userRegisterForm.getUserPassword(),
                userRegisterForm.getCheckPassword()));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @WebLog
    public Result<String> userLogin(@RequestBody @Valid UserLoginForm userLoginForm, HttpServletRequest request) {
        return Result.success(this.apiUserService.userLogin(userLoginForm.getVerificationCode(),
                userLoginForm.getUserAccount(),
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

    @PostMapping("/list/page")
    @Operation(summary = "分页查询用户信息")
    @PreAuthorize("admin")
    public Result<Page<ApiUser>> listUserInfoByPage(@RequestBody @Valid QueryUserInfoForm queryUserInfoForm) {
        return Result.success(this.apiUserService.listUserInfoByPage(queryUserInfoForm));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户信息")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> deleteUserInfo(@RequestBody DeleteUserInfoForm deleteUserInfoForm) {
        if (deleteUserInfoForm == null || deleteUserInfoForm.getIds() == null) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        Long[] ids = deleteUserInfoForm.getIds();
        verifyUserIds(ids);
        long res = this.apiUserService.deleteUserInfo(ids);
        return Result.success(res == ids.length ? ids.length : 0L);
    }

    @PutMapping("/forbid/{id}")
    @Operation(summary = "禁用用户")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> forbidUser(@PathVariable("id") Long id) {
        if (id == null || id < 1) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        boolean res = this.apiUserService.forbidUser(id);
        return Result.success(res ? 1L : 0L);
    }

    @PutMapping("/permit/{id}")
    @Operation(summary = "启用用户")
    @PreAuthorize("admin")
    @WebLog
    public Result<Long> permitUser(@PathVariable("id") Long id) {
        if (id == null || id < 1) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        boolean res = this.apiUserService.permitUser(id);
        return Result.success(res ? 1L : 0L);
    }

    private void verifyUserIds(Long[] ids) {
        if (ids.length == 0) {
            throw new BasicException(ErrorCode.PARAMS_ERROR);
        }
        for (long id : ids) {
            if (id < 1) {
                throw new BasicException(ErrorCode.PARAMS_ERROR);
            }
        }
    }
}