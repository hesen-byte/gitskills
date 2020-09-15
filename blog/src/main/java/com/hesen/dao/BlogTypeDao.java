package com.hesen.dao;

import com.hesen.entity.BlogType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("blogTypeDao")
public interface BlogTypeDao
{
    public List<BlogType> countList();

    public BlogType findById(Integer id);

    public List<BlogType> list(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer addBlogType(BlogType blogType);

    public Integer updateBlogType(BlogType blogType);

    public Integer deleteBlogType(Integer id);
}
