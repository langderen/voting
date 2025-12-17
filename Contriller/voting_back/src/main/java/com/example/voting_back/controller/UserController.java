package com.example.voting_back.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.voting_back.common.Result;
import com.example.voting_back.entity.User;
import com.example.voting_back.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@SaCheckLogin
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
     *通过用户email查询用户
     * @param email
     * @return User
     */
    @SaIgnore
    @GetMapping
    public Result getUserByEmail(@RequestParam String email) {
        // 假设有一个 QueryWrapper 对象，设置查询条件为 email = email
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = userService.getOne(queryWrapper); // 调用 getOne 方法
        return Result.success(user.getUserId());
    }

    @DeleteMapping()
    public Result deletePoll(@RequestParam Long id){
        return Result.success(userService.removeById(id));
    }


}