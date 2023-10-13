package com.lg.qapl.request;

import lombok.Data;

@Data
public class CreateQuestionRequest {

    private String questionName;
    private String questionContent;
    private Integer questionTypeId;
    private Integer userId;
}
