package com.lg.qapl.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Boolean isAdmin;
}
