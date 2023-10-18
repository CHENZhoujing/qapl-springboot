package com.lg.qapl.controller;

import com.lg.qapl.request.AnswerQuestionRequest;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import com.lg.qapl.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 实现用户登录逻辑，验证用户名和密码
        // 返回登录结果
        return adminService.login(request);
    }

    // 管理员查看问题列表
    @PostMapping("/view-question")
    public ResponseEntity<?> viewQuestion(@RequestBody ViewQuestionRequest request) {
        // 实现管理员查看问题列表逻辑
        // 返回问题列表
        return adminService.viewQuestions(request);
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
    public ResponseEntity<?> answerQuestion(@RequestBody AnswerQuestionRequest request) {
        // 实现管理员回答问题逻辑
        // 返回回答结果
        return adminService.answerQuestion(request);
    }
}
