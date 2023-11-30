package com.lg.qapl.request;

import lombok.Data;

@Data
public class ViewQuestionResponse {
    private Integer userId;
    private String username;
    private Integer questionTypeId;
    private String questionTypeName;
    private Integer questionId;
    private String questionContent;
    private String questionCreateTime;
    private String questionAnswer;
    private String questionAnswerTime;

}

