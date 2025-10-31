package com.example.voting_back.controller;

import com.example.voting_back.common.Result;
import com.example.voting_back.entity.Votes;
import com.example.voting_back.service.VotesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voting")
public class VotesController {

    @Resource
    private VotesService votesService;
    /*
    * 为poll_id的活动的某个选项投票
    * */
    @PostMapping
    public Result voting(@RequestBody Votes votes) {
        return Result.success(votesService.save(votes));
    }
}
