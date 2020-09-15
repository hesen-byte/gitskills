package com.hesen.controller.admin;


import com.google.gson.Gson;
import com.hesen.entity.Blog;
import com.hesen.lucene.BlogIndex;
import com.hesen.service.BlogService;
import com.hesen.utils.ResponseUtils;
import com.hesen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/blog", produces = "text/html;charset=utf-8")
public class BlogAdminController
{
    @Autowired
    private BlogService blogService;

    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/save")
    public String saveBlog(Blog blog, HttpServletResponse response) throws Exception
    {
        int rtValue = 0;
        /**通过blog的id属性是否为null来判断是添加还是更新*/
        if (blog.getId() == null)
        {
            rtValue = blogService.addBlog(blog);
            blogIndex.addIndex(blog);
        }
        else
        {
            rtValue = blogService.updateBlog(blog);
            blogIndex.updateIndex(blog);
        }

        Map<String, Object> result = new HashMap<>();
        if (rtValue > 0) result.put("success", true);
        else result.put("success", false);

        return new Gson().toJson(result);
    }

    @RequestMapping("/list")
    public String listBlog(String page, String rows, Blog blog,
                           HttpServletResponse response) throws Exception
    {
        int start = Integer.parseInt(page) - 1;
        int size = Integer.parseInt(rows);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("title", StringUtils.formatLike(blog.getTitle()));

        List<Blog> list = blogService.list(map);
        long total = blogService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", list);
        result.put("total", total);
        return new Gson().toJson(result);
    }


    @RequestMapping("/findById")
    public String findById(String id, HttpServletResponse response) throws Exception
    {
        Blog blog = blogService.findById(Integer.parseInt(id));
        return new Gson().toJson(blog);
    }

    @RequestMapping("/delete")
    public String deleteBlog(String ids, HttpServletResponse response) throws Exception
    {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++)
        {
            blogService.deleteBlog(Integer.parseInt(idsStr[i]));
            blogIndex.deleteIndex(idsStr[i]);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return new Gson().toJson(result);
    }
}
