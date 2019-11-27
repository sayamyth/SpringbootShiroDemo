package com.example.shiro.config;

import com.example.shiro.entity.User;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("认证");

        //通过SecurityUtils得到哦用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        String str[] = user.getPerms().split(",");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (int i = 0;i < str.length;i++) {
            info.addStringPermission(str[i]);
        }
        return info;
    }
    @Autowired
    private UserService userService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行");
        String userName = (String) authenticationToken.getPrincipal();
        System.out.println(userName);
        User user = userService.finUserByName(userName);
        if (user==null){
            return null;
        }

        //判断密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
