package com.hesen.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface BlogTypeService
{
    public List<com.hesen.entity.BlogType> countList();

    public com.hesen.entity.BlogType findById(Integer id);

    public List<com.hesen.entity.BlogType> list(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer addBlogType(com.hesen.entity.BlogType blogType);

    public Integer updateBlogType(com.hesen.entity.BlogType blogType);

    public Integer deleteBlogType(Integer id);
}
