package com.lg.qapl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.qapl.entite.QaplCombined;
import com.lg.qapl.entite.Question;
import com.lg.qapl.entite.QuestionType;
import com.lg.qapl.entite.User;
import com.lg.qapl.mapper.QaplCombinedMapper;
import com.lg.qapl.mapper.QuestionMapper;
import com.lg.qapl.mapper.QuestionTypeMapper;
import com.lg.qapl.mapper.UserMapper;
import com.lg.qapl.request.*;
import com.lg.qapl.service.UserService;
import com.lg.qapl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;
    @Autowired
    private QaplCombinedMapper qaplCombinedMapper;

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
            String token = JwtUtil.generateToken(user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(token); // 返回生成的令牌
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Override
    public ResponseEntity<?> getQuestionType() {
        List<QuestionType> questionTypes = questionTypeMapper.selectList(new LambdaQueryWrapper<QuestionType>()
                .eq(QuestionType::getIsDeleted, false));
        return ResponseEntity.status(HttpStatus.OK).body(questionTypes);
    }

    @Override
    public ResponseEntity<?> askQuestion(String token, CreateQuestionRequest request) {
        // check user
        Integer userId = JwtUtil.getUserIdFromToken(token);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, userId)
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
        question.setQuestionTitle(request.getQuestionTitle());
        question.setQuestionContent(request.getQuestionContent());
        question.setQuestionTypeId(request.getQuestionTypeId());
        question.setUserId(userId);
        question.setCreateTime(now);
        question.setUpdateTime(now);
        questionMapper.insert(question);
        return ResponseEntity.ok("Create question success");
    }

    @Override
    public ResponseEntity<?> updateQuestion(UpdateQuestionRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, request.getUserId())
                .eq(User::getIsAdmin, false)
                .eq(User::getIsDeleted, false));
        if (null == user) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
        }
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                .eq(Question::getQuestionId, request.getQuestionId())
                .eq(Question::getIsDeleted, false));
        Date now = new Date();
        question.setAnswerTime(now);
        question.setQuestionContent(request.getQuestionContent());
        questionMapper.updateById(question);
        return null;
    }

    @Override
    public ResponseEntity<?> viewQuestion(String token, ViewQuestionRequest request) {
        // 实现用户查看问题逻辑
        // 返回问题详情
        // 这里可以调用DAO或者其他服务来获取问题详情
        Integer userId = JwtUtil.getUserIdFromToken(token);

        Page<QaplCombined> rowPage = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<QaplCombined> queryWrapper = new LambdaQueryWrapper<QaplCombined>()
                .eq(QaplCombined::getUserId, userId)
                .eq(QaplCombined::getUserIsDeleted, false)
                .eq(QaplCombined::getQuestionIsDeleted, false)
                .orderByDesc(QaplCombined::getQuestionCreateTime);
        rowPage = qaplCombinedMapper.selectPage(rowPage, queryWrapper);

        return ResponseEntity.status(HttpStatus.OK).body(rowPage); // 返回适当的响应
    }


}
