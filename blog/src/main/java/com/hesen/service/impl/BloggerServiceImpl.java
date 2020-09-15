package com.hesen.service.impl;

import com.hesen.dao.BloggerDao;
import com.hesen.entity.Blogger;
import com.hesen.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService
{
    @Resource
    private BloggerDao bloggerDao;

    @Override
    public Blogger getByUsername(String userName)
    {
        return bloggerDao.getByUsername(userName);
    }

    @Override
    public Integer updateBlogger(Blogger blogger)
    {
        SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
        return bloggerDao.updateBlogger(blogger);
    }

    @Override
    public Blogger findBlogger()
    {
        return bloggerDao.findBlogger();
    }
}
