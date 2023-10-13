package com.lg.qapl.entite;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("question_type")
public class QuestionType {
    @TableId(type = IdType.AUTO)
    @TableField(value = "question_type_id")
    private Integer questionTypeId;
    @TableField(value = "type_name")
    private String typeName;
}