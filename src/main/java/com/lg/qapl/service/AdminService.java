package com.lg.qapl.service;

import com.lg.qapl.request.AdminUpdatePasswordRequest;
import com.lg.qapl.request.AnswerQuestionRequest;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<?> viewQuestions(String token, ViewQuestionRequest request);
    ResponseEntity<?> deleteQuestion(String token, Integer questionId);
    ResponseEntity<?> answerQuestion(String token, AnswerQuestionRequest request);
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> updatePassword(String token, AdminUpdatePasswordRequest request);
}
