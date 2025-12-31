package com.example.voting_back.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.voting_back.entity.Poll;

@Mapper
public interface PollMapper extends BaseMapper<Poll> {
}
