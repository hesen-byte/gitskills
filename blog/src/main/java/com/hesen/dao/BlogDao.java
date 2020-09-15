package com.hesen.dao;

import com.hesen.entity.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogDao
{
    public List<Blog> countList();

    public List<Blog> list(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer addBlog(Blog blog);

    public Integer deleteBlog(Integer id);

    public Integer updateBlog(Blog blog);

    public Blog findById(Integer id);

    public Integer findBlogByTypeId(Integer typeId);

    public Blog getLastBlog(Integer id);

    public Blog getNextBlog(Integer id);
}
