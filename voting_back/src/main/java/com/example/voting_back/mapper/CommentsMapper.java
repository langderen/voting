package com.example.voting_back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.voting_back.entity.Comments;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {
}
