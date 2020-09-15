package com.hesen.controller;

import com.hesen.entity.Blog;
import com.hesen.service.BlogService;
import com.hesen.utils.PageUtils;
import com.hesen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IndexController主要用来更新博客列表所对应的模型
 */
@Controller
public class IndexController
{
    @Autowired
    private BlogService blogService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, String page,
                              String typeId, String releaseDateStr)
    {
        if (StringUtils.isEmpty(page)) page = "1";
        int start = Integer.parseInt(page) - 1;
        int size = 10;
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<Blog> blogList = blogService.list(map);

        /**根据博客类别，或博客发布时间更新博客列表*/
        StringBuffer param = new StringBuffer();
        if (StringUtils.isNotEmpty(typeId))
            param.append("typeId=" + typeId + "&");

        if (StringUtils.isNotEmpty(releaseDateStr))
            param.append("releaseDateStr=" + releaseDateStr + "&");

        ModelAndView mav = new ModelAndView();

        /**调用PageUtils生成前端分页的html代码并返回给前端*/
        mav.addObject("pageCode", PageUtils.genPagination(request.getContextPath() + "/index",
                    blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
        mav.addObject("title", "个人博客系统");
        mav.addObject("blogList", blogList);
        mav.addObject("mainPage", "foreground/blog/list.jsp");
        mav.setViewName("mainTemplate");
        return mav;
    }
}
