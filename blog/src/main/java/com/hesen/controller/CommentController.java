package com.hesen.controller;

import com.google.gson.Gson;
import com.hesen.entity.Blog;
import com.hesen.entity.Comment;
import com.hesen.service.BlogService;
import com.hesen.service.CommentService;
import com.hesen.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/save")
    public String saveComment(Comment comment, String imageCode,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session) throws Exception
    {
        Map<String, Object> result = new HashMap<>();
        int rtValue = 0;
        //id为null说明是增加，否则是更新
        if (comment.getId() == null)
        {
            comment.setUserIp(request.getRemoteAddr());
            rtValue = commentService.addComment(comment);
            Blog blog = blogService.findById(comment.getBlog().getId());
            blog.setReplyHit(blog.getReplyHit() + 1);
            blogService.updateBlog(blog);
        }
        if (rtValue > 0)
            result.put("success", true);
        else
            result.put("success", false);

        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }
}
