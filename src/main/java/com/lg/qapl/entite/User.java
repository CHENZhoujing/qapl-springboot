package com.lg.qapl.entite;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("User")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userID;
    private String username;
    private String password;
    private String email;
    private String phone;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Boolean isDeleted;
}