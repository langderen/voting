package com.example.voting_back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.voting_back.entity.Votes;
import com.example.voting_back.mapper.VotesMapper;
import com.example.voting_back.service.VotesService;
import org.springframework.stereotype.Service;

@Service
public class VotesServiceImpl extends ServiceImpl<VotesMapper, Votes> implements VotesService {
}
