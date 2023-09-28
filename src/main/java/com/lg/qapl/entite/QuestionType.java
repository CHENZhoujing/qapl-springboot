package com.lg.qapl.entite;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableId;

public class QuestionType {
    @TableId(type = IdType.AUTO)
    private Integer questionTypeID;
    private String typeName;
}