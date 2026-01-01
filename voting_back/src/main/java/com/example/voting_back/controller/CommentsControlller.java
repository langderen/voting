package com.example.voting_back.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.voting_back.common.Result;
import com.example.voting_back.entity.Comments;
import com.example.voting_back.service.CommentsService;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;

@SaIgnore
@RestController
@RequestMapping("/comments")
public class CommentsControlller {
    @Resource
    private CommentsService commentsService;
    /*
    通过id获取该活动下的评论*/
    @GetMapping()
    public Result getCommentById(@RequestParam int id){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("poll_id", id);
        Collection<Comments> comments = commentsService.listByMap(columnMap);
        return Result.success(comments);
    }
    @PostMapping
    public Result addComment(@RequestBody Comments comments){

        return Result.success(commentsService.save(comments));
    }
}
