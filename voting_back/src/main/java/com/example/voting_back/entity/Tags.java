package com.example.voting_back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("tags")
public class Tags {
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;
    @TableField("tag_name")
    private String tagName;

}
