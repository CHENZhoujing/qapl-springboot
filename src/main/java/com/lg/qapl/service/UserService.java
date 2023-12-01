package com.lg.qapl.service;

import com.lg.qapl.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> getQuestionType();
    ResponseEntity<?> askQuestion(String token, CreateQuestionRequest request);
    ResponseEntity<?> updateQuestion(UpdateQuestionRequest request);
    ResponseEntity<?> viewQuestion(String token, ViewQuestionRequest request);
    ResponseEntity<?> updatePassword(String token, UserUpdatePasswordRequest request);
}
