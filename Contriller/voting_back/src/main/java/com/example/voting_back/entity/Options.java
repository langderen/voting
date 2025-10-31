package com.example.voting_back.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("poll_options")
@Data
public class Options {
    @TableId
    @TableField(value = "option_id",fill= FieldFill.INSERT)
    private Integer optionId;
    @TableField("poll_id")
    private Integer pollId;
    @TableField("option_text")
    private String optionText;
    @TableField("image_url")
    private String imageUrl;
    @TableField("count")
    private Long count;

}
