package com.lg.qapl.request;

import lombok.Data;

@Data
public class UserUpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
