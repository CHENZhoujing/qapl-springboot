package com.lg.qapl.service;

import com.lg.qapl.Request.AnswerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<?> viewQuestions();
    ResponseEntity<?> deleteQuestion(Long questionId);
    ResponseEntity<?> answerQuestion(AnswerRequest request);
}
