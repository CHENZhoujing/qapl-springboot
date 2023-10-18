package com.lg.qapl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.qapl.entite.Question;
import com.lg.qapl.entite.QuestionType;
import com.lg.qapl.entite.User;
import com.lg.qapl.mapper.QuestionMapper;
import com.lg.qapl.mapper.QuestionTypeMapper;
import com.lg.qapl.mapper.UserMapper;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.CreateQuestionRequest;
import com.lg.qapl.request.ViewQuestionRequest;
import com.lg.qapl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        if (null == request.getUsername() || null == request.getPassword()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
                .eq(User::getIsAdmin, false)
                .eq(User::getIsDeleted, false));
        if (null != user && user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok("Login successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Override
    public ResponseEntity<?> askQuestion(CreateQuestionRequest request) {
        // check user
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, request.getUserId())
                .eq(User::getIsAdmin, false)
                .eq(User::getIsDeleted, false));
        if (null == user) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
        }
        // check question type
        QuestionType questionType = questionTypeMapper.selectOne(new LambdaQueryWrapper<QuestionType>()
                .eq(QuestionType::getQuestionTypeId, request.getQuestionTypeId())
                .eq(QuestionType::getIsDeleted, false));
        if (null == questionType) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid question type");
        }
        // create question
        Date now = new Date();
        Question question = new Question();
        question.setAnswer(question.getAnswer());
        question.setQuestionContent(request.getQuestionContent());
        question.setQuestionTypeId(request.getQuestionTypeId());
        question.setUserId(request.getUserId());
        question.setCreateTime(now);
        question.setUpdateTime(now);
        questionMapper.insert(question);
        return ResponseEntity.ok("Create question success");
    }

    @Override
    public ResponseEntity<?> viewQuestion(ViewQuestionRequest request) {
        // 实现用户查看问题逻辑
        // 返回问题详情
        // 这里可以调用DAO或者其他服务来获取问题详情
        Page<Question> rowPage = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<Question>()
                .eq(Question::getUserId, request.getUserId())
                .eq(Question::getIsDeleted, false);
        rowPage = questionMapper.selectPage(rowPage, queryWrapper);
        return ResponseEntity.ok(rowPage); // 返回适当的响应
    }
}
