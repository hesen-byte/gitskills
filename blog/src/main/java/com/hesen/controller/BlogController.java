package com.hesen.controller;

import com.hesen.entity.Blog;
import com.hesen.lucene.BlogIndex;
import com.hesen.service.BlogService;
import com.hesen.service.CommentService;
import com.hesen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog")
public class BlogController
{
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    private BlogIndex blogIndex = new BlogIndex();

    /**
     *获得某一篇具体博客的详细信息
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();

        /**每点击一次，博客的阅读数加一*/
        Blog blog = blogService.findById(id);
        blog.setClickHit(blog.getClickHit() + 1);
        blogService.updateBlog(blog);

        mav.addObject("blog", blog);
        mav.addObject("mainPage", "foreground/blog/view.jsp");
        mav.addObject("pageTitle", blog.getTitle() + "_个人博客系统");

        Blog lastBlog = blogService.getLastBlog(id);
        Blog nextBlog = blogService.getNextBlog(id);
        String projectContext = request.getServletContext().getContextPath();
        mav.addObject("pageCode", genUpAndDownPageCode(lastBlog, nextBlog, projectContext));

        Map<String, Object> map = new HashMap<>();
        map.put("blogId", id);
        map.put("state", 1);
        /**获得本篇博客对应的所有评论*/
        mav.addObject("commentList", commentService.listComment(map));

        //处理关键字
        String keyWord = blog.getKeyWord();
        if (StringUtils.isEmpty(keyWord))
            mav.addObject("keyWords", null);
        else
        {
            String[] arr = keyWord.split("\\s+");
            mav.addObject("keyWords", Arrays.asList(arr));
        }

        mav.setViewName("mainTemplate");
        return mav;
    }

    /**
     * 帮助生成博客详情页的上下篇html代码
     */
    private String genUpAndDownPageCode(Blog lastBlog, Blog nextBlog, String projectContext)
    {
        StringBuffer pageCode = new StringBuffer();
        if ((lastBlog == null) || (lastBlog.getId() == null)) {
            pageCode.append("<p>上一篇：没有了</p>");
        } else {
            pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + "'>" + lastBlog.getTitle() + "</a></p>");
        }
        if ((nextBlog == null) || (nextBlog.getId() == null)) {
            pageCode.append("<p>下一篇：没有了</p>");
        } else {
            pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + "'>" + nextBlog.getTitle() + "</a></p>");
        }
        return pageCode.toString();
    }

    /**
     *用于获取搜索到的结果
     */
    @RequestMapping("/q")
    public ModelAndView query(String q, String page, HttpServletRequest request) throws Exception
    {
        if (StringUtils.isEmpty(page))
        {
            page = "1";
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "foreground/blog/result.jsp");
        List<Blog> blogList = blogIndex.searchBlog(q.trim());
        mav.addObject("q", q);
        mav.addObject("resultTotal", blogList.size());
        mav.addObject("blogList", blogList);
        mav.addObject("pageTitle", "搜索关键字" + q + "结果页面_个人博客");
        mav.setViewName("mainTemplate");
        return mav;
    }


}
