package com.example.shiro.service;

import com.example.shiro.entity.User;

public interface UserService {
    //根据用户名查询用户
    User finUserByName(String name);

    //
}
