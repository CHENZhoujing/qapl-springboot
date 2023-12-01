package com.lg.qapl.service;

import com.lg.qapl.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<?> viewQuestions(String token, ViewQuestionRequest request);
    ResponseEntity<?> deleteQuestion(String token, Integer questionId);
    ResponseEntity<?> answerQuestion(String token, AnswerQuestionRequest request);
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> updatePassword(String token, AdminUpdatePasswordRequest request);
    ResponseEntity<?> deleteUser(String token, Integer userId);
    ResponseEntity<?> viewUser(String token, ViewUserRequest request);
    ResponseEntity<?> createUser(String token, CreateUserRequest request);
    ResponseEntity<?> updateUser(String token, UpdateUserRequest request);
}
