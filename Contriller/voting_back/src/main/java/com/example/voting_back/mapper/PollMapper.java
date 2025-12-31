package com.example.voting_back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.voting_back.entity.Poll;
import com.example.voting_back.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PollMapper extends BaseMapper<Poll> {
}
