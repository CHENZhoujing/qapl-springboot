package com.lg.qapl.entite;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

@Data
@TableName("qapl_combined")
public class QaplCombined {
    @TableId("user_id")
    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "user_email")
    private String userEmail;

    @TableField(value = "user_phone")
    private String userPhone;

    @TableField(value = "user_create_time")
    private Date userCreateTime;

    @TableField(value = "user_is_admin")
    private Boolean userIsAdmin;

    @TableField(value = "user_is_deleted")
    private Boolean userIsDeleted;

    @TableField(value = "question_type_id")
    private Integer questionTypeId;

    @TableField(value = "question_type_name")
    private String questionTypeName;

    @TableField(value = "question_id")
    private Integer questionId;

    @TableField(value = "question_title")
    private String questionTitle;

    @TableField(value = "question_content")
    private String questionContent;

    @TableField(value = "question_create_time")
    private Date questionCreateTime;

    @TableField(value = "question_update_time")
    private Date questionUpdateTime;

    @TableField(value = "question_answer")
    private String questionAnswer;

    @TableField(value = "question_answer_time")
    private Date questionAnswerTime;

    @TableField(value = "question_is_deleted")
    private Boolean questionIsDeleted;
}
