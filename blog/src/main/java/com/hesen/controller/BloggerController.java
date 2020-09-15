package com.hesen.controller;

import com.hesen.entity.Blog;
import com.hesen.entity.Blogger;
import com.hesen.service.BlogService;
import com.hesen.service.BloggerService;
import com.hesen.utils.Encrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blogger")
public class BloggerController
{
    @Autowired
    private BloggerService blogService;

    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest request)
    {
        String userName = blogger.getUserName();
        String password = blogger.getPassword();
        String pw = Encrypt.md5(password, "hesen");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pw);

        try
        {
            //传递token给shiro的realm
            subject.login(token);
            return "redirect:/admin/main.jsp";
        }
        catch (AuthenticationException e)
        {
            e.printStackTrace();
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "用户名或密码错误！");
        }
        return "login";
    }

    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe()
    {
        ModelAndView mav = new ModelAndView();
        Blogger blogger = (Blogger) blogService.findBlogger();
        mav.addObject("blogger", blogger);
        mav.addObject("mainPage", "foreground/blogger/info.jsp");
        mav.addObject("pageTitle", "关于博主_个人博客系统");
        mav.setViewName("mainTemplate");
        return mav;
    }
}
