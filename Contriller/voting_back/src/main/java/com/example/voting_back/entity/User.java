package com.example.voting_back.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("`users`")
public class User {
    @TableId
    @TableField(value = "user_id",fill= FieldFill.INSERT)
    private Integer userId;
    @TableField(value = "username")
    private String username;
    @TableField(value = "email")
    private String email;
    @TableField(value = "password_hash")
    private String passwordHash;
    @TableField(value = "display_name")
    private String displayName;
    @TableField(value = "avatar_url")
    private String avatarUrl;
    @TableField(value = "created_at",fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @TableField(value = "last_login",fill= FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;
    @TableField(value = "is_admin")
    private Boolean isAdmin;
}