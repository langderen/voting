package com.example.voting_back.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.voting_back.common.Result;
import com.example.voting_back.entity.Comments;
import com.example.voting_back.entity.Poll;
import com.example.voting_back.service.CommentsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentsControlller {
    @Resource
    private CommentsService commentsService;
    /*
    通过id获取该活动下的评论*/
    @GetMapping()
    public Result getCommentById(@RequestBody Long id){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("poll_id", id);
        Collection<Comments> comments = commentsService.listByMap(columnMap);
        return Result.success(comments);
    }
    @SaCheckLogin
    @PostMapping
    public Result addComment(@RequestBody Comments comments){

        return Result.success(commentsService.save(comments));
    }
}
