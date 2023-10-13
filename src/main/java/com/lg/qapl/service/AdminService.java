package com.lg.qapl.service;

import com.lg.qapl.request.AnswerRequest;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<?> viewQuestions(ViewQuestionRequest request);
    ResponseEntity<?> deleteQuestion(Long questionId);
    ResponseEntity<?> answerQuestion(AnswerRequest request);
    ResponseEntity<?> login(LoginRequest request);
}
