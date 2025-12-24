package com.example.voting_back.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.voting_back.common.Result;
import com.example.voting_back.entity.User;
import com.example.voting_back.service.UserService;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;

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
    public Result getUserById(@RequestParam int id) {

        User user = userService.getById(id); // 调用 get 方法
        User user1 = new User();
        user1.setUserId(user.getUserId());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        user1.setAvatarUrl(user.getAvatarUrl());
        user1.setCreatedAt(user.getCreatedAt());
        //user1.setPasswordHash(null);
        user1.setIsAdmin(user.getIsAdmin());
        user1.setDisplayName(user.getDisplayName());

        return Result.success(user1);
    }

    @DeleteMapping()
    public Result deletePoll(@RequestParam Long id){
        return Result.success(userService.removeById(id));
    }


}