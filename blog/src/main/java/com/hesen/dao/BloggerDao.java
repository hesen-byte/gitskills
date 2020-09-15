package com.hesen.dao;

import com.hesen.entity.Blogger;
import org.springframework.stereotype.Repository;

@Repository("bloggerDao")
public interface BloggerDao
{
    public Blogger getByUsername(String userName);

    public Integer updateBlogger(Blogger blogger);

    public Blogger findBlogger();
}
