package com.lg.qapl.request;

import lombok.Data;

@Data
public class UpdateQuestionRequest {

    private Long questionId;
    private Long userId;
    private String questionContent;
}
