package com.lg.qapl.request;

import lombok.Data;

@Data
public class ViewQuestionRequest {
    private boolean notAnswered;
    private boolean thisMonth;
    private Integer size;
    private Integer current;
}
