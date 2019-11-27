package com.example.shiro.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shiro.entity.User;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User finUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",name);

        return userMapper.selectOne(queryWrapper);
    }
}
