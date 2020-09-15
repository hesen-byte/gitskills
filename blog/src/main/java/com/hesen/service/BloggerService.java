package com.hesen.service;

import com.hesen.entity.Blogger;

import javax.servlet.http.HttpServletRequest;

public interface BloggerService
{
    public Blogger getByUsername(String userName);

    public Integer updateBlogger(Blogger blogger);

    public Blogger findBlogger();
}
