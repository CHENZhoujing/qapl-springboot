package com.lg.qapl.service;

import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.QuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> askQuestion(QuestionRequest request);
    ResponseEntity<?> viewQuestion(Long questionId);
}
