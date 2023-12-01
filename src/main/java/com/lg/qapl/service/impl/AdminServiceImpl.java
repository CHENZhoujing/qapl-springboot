package com.lg.qapl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.qapl.entite.QaplCombined;
import com.lg.qapl.entite.User;
import com.lg.qapl.entite.Question;
import com.lg.qapl.mapper.QaplCombinedMapper;
import com.lg.qapl.mapper.QuestionMapper;
import com.lg.qapl.mapper.QuestionTypeMapper;
import com.lg.qapl.mapper.UserMapper;
import com.lg.qapl.request.*;
import com.lg.qapl.service.AdminService;
import com.lg.qapl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

    @Autowired
    private QaplCombinedMapper qaplCombinedMapper;

    @Override
    public ResponseEntity<?> viewQuestions(String token, ViewQuestionRequest request) {
        // 实现管理员查看问题列表逻辑
        // 返回问题列表
        // 这里可以调用DAO或者其他服务来获取问题列表
        Integer userId = JwtUtil.getUserIdFromToken(token);
        User user = userMapper.selectById(userId);
        if (null == user || !user.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        Page<QaplCombined> rowPage = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<QaplCombined> queryWrapper = new LambdaQueryWrapper<QaplCombined>()
                .eq(QaplCombined::getUserIsDeleted, false)
                .eq(QaplCombined::getQuestionIsDeleted, false);

        if (request.isNotAnswered()) {
            queryWrapper.isNull(QaplCombined::getQuestionAnswerTime);
        }

        // 如果thisMonth为true，筛选出createTime在当前月份的记录
        if (request.isThisMonth()) {
            LocalDate now = LocalDate.now();
            LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
            queryWrapper.between(QaplCombined::getQuestionCreateTime, firstDayOfMonth, lastDayOfMonth);
        }

        queryWrapper.orderByDesc(QaplCombined::getQuestionCreateTime);

        rowPage = qaplCombinedMapper.selectPage(rowPage, queryWrapper);
        return ResponseEntity.ok(rowPage); // 返回适当的响应
    }

    @Override
    public ResponseEntity<?> deleteQuestion(String token, Integer questionId) {
        // 实现管理员删除问题逻辑
        // 返回删除结果
        // 这里可以调用DAO或者其他服务来处理删除逻辑
        Integer userId = JwtUtil.getUserIdFromToken(token);
        User user = userMapper.selectById(userId);
        if (null == user || !user.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
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
    public ResponseEntity<?> answerQuestion(String token, AnswerQuestionRequest request) {
        // 实现管理员回答问题逻辑
        // 返回回答结果
        // 这里可以调用DAO或者其他服务来处理回答逻辑
        // check user
        Integer userId = JwtUtil.getUserIdFromToken(token);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, userId)
                .eq(User::getIsAdmin, true)
                .eq(User::getIsDeleted, false));
        if (null == user || !user.getIsAdmin()) {
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
            String token = JwtUtil.generateToken(user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(token); // 返回生成的令牌
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @Override
    public ResponseEntity<?> updatePassword(String token, AdminUpdatePasswordRequest request) {
        User admin = userMapper.selectById(JwtUtil.getUserIdFromToken(token));
        if (null == admin || !admin.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        // 实现管理员修改密码逻辑
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, request.getUserId())
                .eq(User::getIsDeleted, false));
        if (null == user) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        user.setPassword(request.getNewPassword());
        userMapper.updateById(user);
        return ResponseEntity.ok("Update password success");
    }

    @Override
    public ResponseEntity<?> deleteUser(String token, Integer userId) {
        User admin = userMapper.selectById(JwtUtil.getUserIdFromToken(token));
        if (null == admin || !admin.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserId, userId)
                .eq(User::getIsDeleted, false));
        if (null == user) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        user.setIsDeleted(true);
        userMapper.updateById(user);
        return ResponseEntity.ok("Delete user success");
    }

    @Override
    public ResponseEntity<?> viewUser(String token, ViewUserRequest request) {
        User admin = userMapper.selectById(JwtUtil.getUserIdFromToken(token));
        if (null == admin || !admin.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        Page<User> rowPage = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, false);
        rowPage = userMapper.selectPage(rowPage, queryWrapper);
        return ResponseEntity.ok(rowPage);
    }

    @Override
    public ResponseEntity<?> createUser(String token, CreateUserRequest request) {
        User admin = userMapper.selectById(JwtUtil.getUserIdFromToken(token));
        if (null == admin || !admin.getIsAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setIsAdmin(request.getIsAdmin());
        user.setIsDeleted(false);
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return ResponseEntity.ok("Create user success");
    }
}
