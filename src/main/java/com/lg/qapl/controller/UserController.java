package com.lg.qapl.controller;

import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.CreateQuestionRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import com.lg.qapl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 实现用户登录逻辑，验证用户名和密码
        // 返回登录结果
        return userService.login(request);
    }

    // 用户提问
    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody CreateQuestionRequest request) {
        // 实现用户提问逻辑
        // 返回提问结果
        return userService.askQuestion(request);
    }

    // 用户查看问题
    @PostMapping("/view-question")
    public ResponseEntity<?> viewQuestion(@RequestBody ViewQuestionRequest request) {
        // 实现用户查看问题逻辑
        // 返回问题详情
        return userService.viewQuestion(request);
    }
}
