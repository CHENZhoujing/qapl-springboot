package com.lg.qapl.entite;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "email")
    private String email;
    @TableField(value = "phone")
    private String phone;
    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private Date createTime;
    @TableField(value = "is_admin")
    private Boolean isAdmin;
    @TableField(value = "is_deleted")
    private Boolean isDeleted;
}