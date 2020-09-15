package com.hesen.controller.admin;

import com.google.gson.Gson;
import com.hesen.entity.BlogType;
import com.hesen.service.BlogService;
import com.hesen.service.BlogTypeService;
import com.hesen.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping(value = "/admin/blogType", produces = "text/html;charset=utf-8")
public class BlogTypeAdminController
{
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    public String list(HttpServletResponse response, String page, String rows) throws Exception
    {
        int size = Integer.parseInt(rows);
        int start = (Integer.parseInt(page) - 1) * size;

        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);

        List<BlogType> list = blogTypeService.list(map);
        long total = blogTypeService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", list);
        result.put("total", total);

        return new Gson().toJson(result);
    }

    @RequestMapping("/save")
    public String save(BlogType blogType, HttpServletResponse response) throws Exception
    {
        int resultValue = 0;
        if (blogType.getId() != null)
            resultValue = blogTypeService.updateBlogType(blogType);
        else
            resultValue = blogTypeService.addBlogType(blogType);

        Map<String, Object> result = new HashMap<>();
        if (resultValue > 0) result.put("success", true);
        else result.put("success", false);
        return new Gson().toJson(result);
    }

    @RequestMapping("/delete")
    public String delete(String ids, HttpServletResponse response) throws Exception
    {
        String[] idsStr = ids.split(",");
        Map<String, Object> result = new HashMap<>();

        for (int i = 0; i < idsStr.length; i++)
        {
            int count = blogService.findBlogByTypeId(Integer.parseInt(idsStr[i]));
            if (count > 0) result.put("exist", "该博客类别下有博客，不能删除！");
            else blogTypeService.deleteBlogType(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return new Gson().toJson(result);
    }


}
