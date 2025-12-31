package com.example.voting_back.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.voting_back.entity.User;
import com.example.voting_back.service.UserService;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

@RequestMapping("/login")
@RestController
public class LoginController {
    private UserService userService;
        // 构造器注入
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    // 登录接口
    @RequestMapping("doLogin")
    public SaResult doLogin(String name, String pwd) {
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        // 假设有一个 QueryWrapper 对象，设置查询条件为 name = name
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(name.matches(em)){
            // 邮箱登录
            queryWrapper.eq("email", name);
        }else{
            // 用户名登录   
            queryWrapper.eq("username", name);
        }
        User user = userService.getOne(queryWrapper); // 调用 getOne 方法
        if(user.getUsername().equals(name) || user.getEmail().equals(name)) {
            if(user.getPasswordHash().equals(pwd)) {
                // 第1步，先登录上 
                StpUtil.login(user.getUserId());
                // 第2步，获取 Token  相关参数 
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                // 第3步，返回给前端 
                return SaResult.data(tokenInfo);
                
            }
        }
        return SaResult.error("登录失败");
    }

    // 查询登录状态  ---- http://localhost:8080/acc/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    // 查询 Token 信息  ---- http://localhost:8080/acc/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8080/acc/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }
}
