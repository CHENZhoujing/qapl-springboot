package com.lg.qapl.service;

import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.CreateQuestionRequest;
import com.lg.qapl.request.UpdateQuestionRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> getQuestionType();
    ResponseEntity<?> askQuestion(String token, CreateQuestionRequest request);
    ResponseEntity<?> updateQuestion(UpdateQuestionRequest request);
    ResponseEntity<?> viewQuestion(String token, ViewQuestionRequest request);
}
