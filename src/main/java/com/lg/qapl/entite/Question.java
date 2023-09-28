package com.lg.qapl.entite;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("Question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer questionID;
    private String questionName;
    private String questionContent;
    private Integer questionType;
    private Integer userID;
    private Date createTime;
    private Date updateTime;
    private Boolean isDeleted;
}
