package com.example.voting_back.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.voting_back.common.Result;
import com.example.voting_back.entity.Poll;
import com.example.voting_back.mapper.PollMapper;
import com.example.voting_back.service.PollService;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;

@SaCheckLogin
@RestController
@RequestMapping("/poll")
public class PollController {
    @Resource
    private PollService pollService ;

    @Resource
    private PollMapper pollMapper;
    /*
     * 创建新的投票活动
     * */

    @PostMapping
    public Result newPoll(@RequestBody Poll poll) {
        pollMapper.insert(poll);
        return Result.success(poll);
    }



    /*
     * 分页获取所有投票列表
     */
    @SaIgnore
    @GetMapping("/votedlist")
    public Result getVotesList(@RequestParam(defaultValue = "1")int pageNum ) {
        IPage<Map<String, Object>> page = new Page<>(pageNum, 12);
        QueryWrapper<Poll> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","closed");
        IPage<Map<String, Object>> pollPageMaps = pollService.pageMaps(page, queryWrapper); // 调用 pageMaps 方法
        List<Map<String, Object>> pollMapList = pollPageMaps.getRecords();
        return Result.success(pollMapList);
    }



    @SaIgnore
    @GetMapping("/voteslist")
    public Result getVotedList(@RequestParam(defaultValue = "1")int pageNum ) {

        // 假设有一个 QueryWrapper 对象，设置查询条件为 status=1，进行有条件的分页查询，并将结果映射为 Map
        IPage<Map<String, Object>> page = new Page<>(pageNum, 12);
        QueryWrapper<Poll> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","active").eq("is_public", 1).orderByDesc("created_at"); // 按创建时间倒序;
        IPage<Map<String, Object>> pollPageMaps = pollService.pageMaps(page, queryWrapper); // 调用 pageMaps 方法
        List<Map<String, Object>> pollMapList = pollPageMaps.getRecords();
        return Result.success(pollMapList);
    }
    /*
     * 通过创建者id获取创建者创建的活动
     * */

    @GetMapping("/getvote")
    public Result getVotesById(@RequestParam int creatorId ){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("creator_id", creatorId);
        Collection<Poll> polls = pollService.listByMap(columnMap);
        return Result.success(polls);
    }
    @DeleteMapping()
    public Result deletePoll(@RequestParam Long id){
        return Result.success(pollService.removeById(id));
    }

    /*
     * 修改活动信息*/
    @PutMapping
    public  Result updatePoll(@RequestBody Poll poll){

        return Result.success(pollService.updateById(poll));
    }

    @GetMapping("/poll")
    public Result getPollById(@RequestParam int pollId ){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("poll_id", pollId);
        Collection<Poll> poll = pollService.listByMap(columnMap);
        return Result.success(poll);
    }
}
