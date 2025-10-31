package com.example.voting_back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.voting_back.entity.Poll;
import com.example.voting_back.mapper.PollMapper;
import com.example.voting_back.service.PollService;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl extends ServiceImpl<PollMapper, Poll> implements PollService {
}
