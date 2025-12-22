package com.example.voting_back.entity;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@TableName("votes")
@Data
public class Votes {
    @TableId(value = "vote_id", type = IdType.AUTO)
    private Integer voteId;
    @TableField("poll_id")
    private Integer pollId;
    @TableField("option_id")
    private Integer optionId;
    @TableField("user_id")
    private Integer userId; // 允许为null（匿名投票）
    @TableField(value="voted_at",fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime votedAt;
    @TableField("ip_address")
    private String ipAddress;

}
