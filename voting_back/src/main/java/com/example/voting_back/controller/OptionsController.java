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
import com.example.voting_back.entity.Options;
import com.example.voting_back.service.OptionsService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/options")
public class OptionsController {

    @Resource
    private OptionsService optionsService;

    /*
     * 通过pollid获取该活动的选项内容
     **/
    @GetMapping()
    public Result getOptionssByVoteId(@RequestParam int pollId ){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("poll_id", pollId);
        Collection<Options> options = optionsService.listByMap(columnMap);
        return Result.success(options);
    }

    @PostMapping
    public Result addOptions(@RequestBody Options options){
        return Result.success(optionsService.save(options));
    }

}
