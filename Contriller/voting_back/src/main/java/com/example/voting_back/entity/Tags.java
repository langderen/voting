package com.example.voting_back.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tags")
public class Tags {
    @TableId
    @TableField(value = "tag_id",fill= FieldFill.INSERT)
    private Integer tagId;
    @TableField("tag_name")
    private String tagName;

}
