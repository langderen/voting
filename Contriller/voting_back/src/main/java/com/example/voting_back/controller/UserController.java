package com.example.voting_back.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.voting_back.common.Result;
import com.example.voting_back.entity.User;
import com.example.voting_back.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /*
    * 新建用户*/
    @PostMapping
    public Result addUser(@RequestBody User user) {
        return Result.success(userService.save(user));
    }
    /*
     * 通过id修改用户
     * */

    @PutMapping
    public  Result updateUser(@RequestBody User user){

        return Result.success(userService.updateById(user));
    }
    /*
    *通过用户id查询用户
    * @param id
    * @return
    */

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id) {
        return Result.success(userService.getOptById(id));
    }

    @DeleteMapping("/{id}")
    public Result deletePoll(@PathVariable Long id){
        return Result.success(userService.removeById(id));
    }

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public SaResult doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            // 第3步，返回给前端
            return SaResult.data(tokenInfo);
        }
        return null;
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
}
