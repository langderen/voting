package com.example.voting_back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.voting_back.entity.User;
import com.example.voting_back.mapper.UserMapper;
import com.example.voting_back.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
