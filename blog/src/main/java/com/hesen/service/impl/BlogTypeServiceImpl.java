package com.hesen.service.impl;

import com.hesen.dao.BlogTypeDao;
import com.hesen.entity.BlogType;
import com.hesen.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService
{
    @Autowired
    private BlogTypeDao blogTypeDao;

    @Override
    public List<BlogType> countList()
    {
        return blogTypeDao.countList();
    }

    @Override
    public BlogType findById(Integer id)
    {
        return blogTypeDao.findById(id);
    }

    @Override
    public List<BlogType> list(Map<String, Object> map)
    {
        return blogTypeDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map)
    {
        return blogTypeDao.getTotal(map);
    }

    @Override
    public Integer addBlogType(BlogType blogType)
    {
        return blogTypeDao.addBlogType(blogType);
    }

    @Override
    public Integer updateBlogType(BlogType blogType)
    {
        return blogTypeDao.updateBlogType(blogType);
    }

    @Override
    public Integer deleteBlogType(Integer id)
    {
        return blogTypeDao.deleteBlogType(id);
    }
}
