package com.lg.qapl.request;

import lombok.Data;

@Data
public class ViewQuestionRequest {
    private Long userId;
    private Integer size;
    private Integer current;
}
