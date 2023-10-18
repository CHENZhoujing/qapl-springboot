package com.lg.qapl.request;

import lombok.Data;

@Data
public class AnswerQuestionRequest {

    private Integer userId;
    private Long questionId;
    private String answerContent;
}
