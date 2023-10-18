package com.lg.qapl.service;

import com.lg.qapl.request.AnswerQuestionRequest;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<?> viewQuestions(ViewQuestionRequest request);
    ResponseEntity<?> deleteQuestion(Long questionId);
    ResponseEntity<?> answerQuestion(AnswerQuestionRequest request);
    ResponseEntity<?> login(LoginRequest request);
}
