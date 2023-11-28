package com.lg.qapl.request;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class QuestionExport {
    @ExcelProperty(value = "提问者", index = 0)
    private String userName;
    @ExcelProperty(value = "问题类型", index = 1)
    private String questionTypeName;
    @ExcelProperty(value = "问题内容", index = 2)
    private String questionContent;
    @ExcelProperty(value = "提问时间", index = 3)
    private Date createTime;
    @ExcelProperty(value = "回答内容", index = 4)
    private String answer;
    @ExcelProperty(value = "回答时间", index = 5)
    private Date answerTime;
}
