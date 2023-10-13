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
        String username = request.getUsername();
        String password = request.getPassword();

        if (isValidUser(username, password)) {
            // 如果用户名和密码有效，生成JWT令牌
            // String jwtToken = JwtUtils.getJwtToken(username, "user's nickname"); // 替换成实际的用户昵称
            // 返回令牌给客户端
            // return ResponseEntity.ok(jwtToken);
            return ResponseEntity.ok("Login successfully");
        } else {
            // 如果验证失败，返回错误响应
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    private boolean isValidUser(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getIsAdmin, false)
                .eq(User::getIsDeleted, false));
        return null != user && user.getPassword().equals(password);
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
        question.setQuestionName(request.getQuestionName());
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
