package com.example.shiro.controller;


import com.example.shiro.entity.User;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class JumpController {
    @Autowired
    private UserService userService;

    @RequestMapping("test1")
    public String test1() {
        return "test1";
    }

    @RequestMapping("test")
    public String test() {
        return "test";
    }

    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("success")
    public String success() {
        return "success";
    }

    @RequestMapping("no")
    public String no() {
        return "no";
    }

    @RequestMapping("login")
    public String login(String username, String password, Model model) {
        System.out.println(username + "..." + password);
        User user = null;
        user = userService.finUserByName(username);
        System.out.println(user);

        /**
         * 使用shiro编写登陆认证
         */

        //获取subject
        Subject subject = SecurityUtils.getSubject();

        //封装用户数据username，password
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //执行登陆方法,判断用户信息
        try {
            subject.login(token);
            return "success";
        } catch (UnknownAccountException e) {
            System.out.println("用户不存在");
//            e.printStackTrace();
            model.addAttribute("msg","用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
            System.out.println("用户密码错误");
            model.addAttribute("msg","用户密码错误");
            return "login";
        }

    }
}
