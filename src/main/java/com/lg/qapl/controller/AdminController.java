package com.lg.qapl.controller;

import com.lg.qapl.request.*;
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
    public ResponseEntity<?> viewQuestion(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ViewQuestionRequest request) {
        // 实现管理员查看问题列表逻辑
        // 返回问题列表
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.viewQuestions(token, request);
    }

    // 管理员删除问题
    @PostMapping("/delete-question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Integer questionId) {
        // 实现管理员删除问题逻辑
        // 返回删除结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.deleteQuestion(token, questionId);
    }

    // 管理员回答问题
    @PostMapping("/answer-question")
    public ResponseEntity<?> answerQuestion(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody AnswerQuestionRequest request) {
        // 实现管理员回答问题逻辑
        // 返回回答结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.answerQuestion(token, request);
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody AdminUpdatePasswordRequest request) {
        // 实现管理员修改密码逻辑
        // 返回修改结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.updatePassword(token, request);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CreateUserRequest request) {
        // 实现管理员创建用户逻辑
        // 返回创建结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.createUser(token, request);
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody UpdateUserRequest request) {
        // 实现管理员修改用户逻辑
        // 返回修改结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.updateUser(token, request);
    }

    @PostMapping("/view-user")
    public ResponseEntity<?> viewUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ViewUserRequest request) {
        String token = authorizationHeader.substring("Bearer ".length());
        // 实现管理员查看用户列表逻辑
        // 返回用户列表
        return adminService.viewUser(token, request);
    }

    @PostMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Integer userId) {
        // 实现管理员删除用户逻辑
        // 返回删除结果
        String token = authorizationHeader.substring("Bearer ".length());
        return adminService.deleteUser(token, userId);
    }
}
