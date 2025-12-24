package com.example.voting_back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.voting_back.entity.Options;
import com.example.voting_back.mapper.OptionsMapper;
import com.example.voting_back.service.OptionsService;
import org.springframework.stereotype.Service;

@Service
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {
}
