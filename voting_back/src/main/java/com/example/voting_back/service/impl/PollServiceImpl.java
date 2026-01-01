package com.example.voting_back.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.voting_back.entity.Poll;
import com.example.voting_back.mapper.PollMapper;
import com.example.voting_back.service.PollService;


@Service
public class PollServiceImpl extends ServiceImpl<PollMapper, Poll> implements PollService {

}
