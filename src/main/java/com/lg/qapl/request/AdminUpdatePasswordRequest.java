package com.lg.qapl.request;

import lombok.Data;

@Data
public class AdminUpdatePasswordRequest {
    private Integer userId;
    private String newPassword;
}
