package com.example.voting_back.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comments")
public class Comments {
    @TableId
    @TableField(value = "comment_id",fill= FieldFill.INSERT)
    private Integer commentId;
    @TableField("poll_id")
    private Integer pollId;
    @TableField("user_id")
    private Integer userId;
    @TableField("content")
    private String content;
    @TableField(value = "created_at",fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @TableField("parent_comment_id")
    private Integer parentCommentId; // 可为null（表示顶级评论）

}
