package com.lg.qapl.controller;

import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.CreateQuestionRequest;
import com.lg.qapl.request.UserUpdatePasswordRequest;
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
    public ResponseEntity<?> askQuestion(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CreateQuestionRequest request) {
        // 实现用户提问逻辑
        // 返回提问结果
        String token = authorizationHeader.substring("Bearer ".length());
        return userService.askQuestion(token, request);
    }

    // 用户查看问题
    @PostMapping("/view-question")
    public ResponseEntity<?> viewQuestion(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ViewQuestionRequest request) {
        // 实现用户查看问题逻辑
        // 返回问题详情
        String token = authorizationHeader.substring("Bearer ".length());
        return userService.viewQuestion(token, request);
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> UpdatePassword(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody UserUpdatePasswordRequest request) {
        // 实现用户修改密码逻辑
        // 返回修改结果
        String token = authorizationHeader.substring("Bearer ".length());
        return userService.updatePassword(token, request);
    }

    @GetMapping("/get-question-type")
    public ResponseEntity<?> getQuestionType() {
        // 实现获取问题类型逻辑
        // 返回问题类型
        return userService.getQuestionType();
    }
}
