package com.hesen.service.impl;

import com.hesen.dao.BlogDao;
import com.hesen.dao.CommentDao;
import com.hesen.entity.Blog;
import com.hesen.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService
{
    @Autowired
    private BlogDao blogDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Blog> countList()
    {
        return blogDao.countList();
    }

    @Override
    public List<Blog> list(Map<String, Object> map)
    {
        return blogDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map)
    {
        return blogDao.getTotal(map);
    }

    @Override
    public Integer addBlog(Blog blog)
    {
        return blogDao.addBlog(blog);
    }

    @Override
    public Integer deleteBlog(Integer id)
    {
        commentDao.deleteCommentByBlogId(id);
        return blogDao.deleteBlog(id);
    }

    @Override
    public Integer updateBlog(Blog blog)
    {
        return blogDao.updateBlog(blog);
    }

    @Override
    public Blog findById(Integer id)
    {
        return blogDao.findById(id);
    }

    @Override
    public Integer findBlogByTypeId(Integer typeId)
    {
        return blogDao.findBlogByTypeId(typeId);
    }

    @Override
    public Blog getLastBlog(Integer id)
    {
        return blogDao.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id)
    {
        return blogDao.getNextBlog(id);
    }


}
