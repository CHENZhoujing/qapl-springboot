package com.lg.qapl.request;

import lombok.Data;

@Data
public class CreateQuestionRequest {

    private String questionContent;
    private String answer;
    private Integer questionTypeId;
}
