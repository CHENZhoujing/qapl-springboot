package com.lg.qapl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.qapl.entite.User;
import com.lg.qapl.entite.Question;
import com.lg.qapl.mapper.QuestionMapper;
import com.lg.qapl.mapper.QuestionTypeMapper;
import com.lg.qapl.mapper.UserMapper;
import com.lg.qapl.request.AnswerQuestionRequest;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import com.lg.qapl.service.AdminService;
import com.lg.qapl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    // 可以注入其他依赖
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Override
    public ResponseEntity<?> viewQuestions(ViewQuestionRequest request) {
        // 实现管理员查看问题列表逻辑
        // 返回问题列表
        // 这里可以调用DAO或者其他服务来获取问题列表
        Page<Question> rowPage = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<Question>()
                .eq(Question::getIsDeleted, false);
        rowPage = questionMapper.selectPage(rowPage, queryWrapper);
        return ResponseEntity.ok(rowPage); // 返回适当的响应
    }

    @Override
    public ResponseEntity<?> deleteQuestion(Long questionId) {
        // 实现管理员删除问题逻辑
        // 返回删除结果
        // 这里可以调用DAO或者其他服务来处理删除逻辑
        Question question = questionMapper.selectById(questionId);
        if (null == question || question.getIsDeleted()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid question");
        }
        question.setIsDeleted(true);
        question.setUpdateTime(new Date());
        questionMapper.updateById(question);
        // 返回适当的响应
        return ResponseEntity.ok("Delete successfully");
    }

    @Override
    public ResponseEntity<?> answerQuestion(AnswerQuestionRequest request) {
        // 实现管理员回答问题逻辑
        // 返回回答结果
        // 这里可以调用DAO或者其他服务来处理回答逻辑
        // check user
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, request.getUserId())
                .eq(User::getIsAdmin, false)
                .eq(User::getIsDeleted, false));
        if (null == user) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
        }
        // check question
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .eq(Question::getQuestionId, request.getQuestionId())
                .eq(Question::getIsDeleted, false));
        if (null == question) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid question");
        }
        question.setAnswer(request.getAnswerContent());
        question.setAnswerTime(new Date());
        questionMapper.updateById(question);
        return ResponseEntity.ok("Answer question success"); // 返回适当的响应
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        if (null == request.getUsername() || null == request.getPassword()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
                .eq(User::getIsAdmin, true)
                .eq(User::getIsDeleted, false));
        if (null != user && user.getPassword().equals(request.getPassword())) {
            String token = JwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token); // 返回生成的令牌
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
