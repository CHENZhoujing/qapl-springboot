package com.lg.qapl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lg.qapl.entite.User;
import com.lg.qapl.mapper.UserMapper;
import com.lg.qapl.request.LoginRequest;
import com.lg.qapl.request.QuestionRequest;
import com.lg.qapl.service.UserService;
import com.lg.qapl.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    // 可以注入其他依赖
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (isValidUser(username, password)) {
            // 如果用户名和密码有效，生成JWT令牌
            String jwtToken = JwtUtils.getJwtToken(username, "user's nickname"); // 替换成实际的用户昵称

            // 返回令牌给客户端
            return ResponseEntity.ok(jwtToken);
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
                .eq(User::getIsDeleted, false));
        return null != user && user.getPassword().equals(password);
    }

    @Override
    public ResponseEntity<?> askQuestion(QuestionRequest request) {
        // 实现用户提问逻辑
        // 返回提问结果
        // 这里可以调用DAO或者其他服务来处理提问逻辑
        return null; // 返回适当的响应
    }

    @Override
    public ResponseEntity<?> viewQuestion(Long questionId) {
        // 实现用户查看问题逻辑
        // 返回问题详情
        // 这里可以调用DAO或者其他服务来获取问题详情
        return null; // 返回适当的响应
    }
}
