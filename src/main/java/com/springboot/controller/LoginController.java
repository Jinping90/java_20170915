package com.springboot.controller;

import com.springboot.pojo.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by BIG on 2017/9/13.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public  String login(){
        return  "loginAndRegist/login";
    }

    @RequestMapping("/toLogin")
    public String toLogin(String username, String password, Model model, HttpSession session){

        User user = userService.findUserByUserName(username);

        if (user != null){
            if (password.equals(user.getPassword())){
                if ("1".equals(user.getUserInfo().getStatment())){
                    session.setAttribute("user",user);
                    String url = (String)session.getAttribute("url");
                    if (url == null || "".equals(url)) {
                        return "redirect:home";
                    } else {
                        return "redirect:" + url;
                    }

                }else {
                    model.addAttribute("errorInfo", "账户未激活");
                    return "loginAndRegist/login";
                }
            }
        }
        model.addAttribute("errorInfo", "用户名或密码错误");
        return "loginAndRegist/login";

    }


}
