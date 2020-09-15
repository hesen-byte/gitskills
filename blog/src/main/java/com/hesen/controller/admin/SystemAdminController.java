package com.hesen.controller.admin;

import com.google.gson.Gson;
import com.hesen.entity.Blog;
import com.hesen.entity.BlogType;
import com.hesen.entity.Blogger;
import com.hesen.entity.Link;
import com.hesen.service.BlogService;
import com.hesen.service.BlogTypeService;
import com.hesen.service.BloggerService;
import com.hesen.service.LinkService;
import com.hesen.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/system")
public class SystemAdminController
{
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private LinkService linkService;

    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ServletContext servletContext = request.getServletContext();

        List<BlogType> list = blogTypeService.countList();
        servletContext.setAttribute("blogTypeCountList", list);

        Blogger blogger = bloggerService.findBlogger();
        servletContext.setAttribute("blogger", blogger);

        List<Blog> blogCountList = blogService.countList();
        servletContext.setAttribute("blogCountList", blogCountList);

        List<Link> linkList = linkService.listLink(null);
        servletContext.setAttribute("linkList", linkList);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }
}
