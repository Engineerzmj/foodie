package com.zmj.controller;

import com.zmj.pojo.Users;
import com.zmj.pojo.bo.UserBO;
import com.zmj.service.UserService;
import com.zmj.utils.CookieUtils;
import com.zmj.utils.JSONResult;
import com.zmj.utils.JsonUtils;
import com.zmj.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用户注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult queryUsernameIsExist(@RequestParam String username) {

        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMap("用户名不能为空");
        }
        if (userService.queryUserNameIsExist(username)) {
            return JSONResult.errorMap("用户名已经存在");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        // 1.用户名或密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2.判断用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        // 3. 密码长度不能少于6位
        if (StringUtils.length(password) < 6) {
            return JSONResult.errorMsg("密码长度不能少于6位");
        }
        // 4. 两次密码是否一致
        if (!StringUtils.equals(password, confirmPwd)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        Users user = userService.createUser(userBO);

        return JSONResult.build(HttpStatus.OK.value(), "创建用户成功", user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        // 1.用户名或密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (users == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        return JSONResult.build(HttpStatus.OK.value(), "登录成功", users);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "user");
        return JSONResult.ok();
    }

}
