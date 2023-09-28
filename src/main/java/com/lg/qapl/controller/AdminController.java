package com.lg.qapl.controller;

import com.lg.qapl.Request.AnswerRequest;
import com.lg.qapl.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 管理员查看问题列表
    @GetMapping("/view-questions")
    public ResponseEntity<?> viewQuestions() {
        // 实现管理员查看问题列表逻辑
        // 返回问题列表
        return adminService.viewQuestions();
    }

    // 管理员删除问题
    @PostMapping("/delete-question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        // 实现管理员删除问题逻辑
        // 返回删除结果
        return adminService.deleteQuestion(questionId);
    }

    // 管理员回答问题
    @PostMapping("/answer-question")
    public ResponseEntity<?> answerQuestion(@RequestBody AnswerRequest request) {
        // 实现管理员回答问题逻辑
        // 返回回答结果
        return adminService.answerQuestion(request);
    }
}
