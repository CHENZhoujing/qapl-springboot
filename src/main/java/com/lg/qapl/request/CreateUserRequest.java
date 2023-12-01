package com.lg.qapl.request;

import lombok.Data;
import java.util.Date;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Boolean isAdmin;
}
