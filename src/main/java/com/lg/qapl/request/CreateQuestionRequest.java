package com.lg.qapl.request;

import lombok.Data;

@Data
public class CreateQuestionRequest {

    private String questionTitle;
    private String questionContent;
    private Integer questionTypeId;
}
