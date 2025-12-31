package com.example.voting_back.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("polls")
public class Poll {
    @TableId
    @TableField(value = "poll_id",fill= FieldFill.INSERT)
    private Integer pollId;
    @TableField("title")
    private String title;
    @TableField("description")
    private String description;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField(value = "created_at",fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @TableField("start_time")
    private String startTime;
    @TableField("end_time")
    private String endTime;
    @TableField("is_public")
    private Boolean isPublic;
    @TableField("is_anonymous")
    private Boolean isAnonymous;
    @TableField("allow_multiple")
    private Boolean allowMultiple;
    @TableField("max_choices")
    private Integer maxChoice;
    @TableField("status")
    private String status;


}