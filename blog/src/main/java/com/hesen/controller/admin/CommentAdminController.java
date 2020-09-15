package com.hesen.controller.admin;

import com.google.gson.Gson;
import com.hesen.entity.Comment;
import com.hesen.service.CommentService;
import com.hesen.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("commentAdminController")
@RequestMapping("/admin/comment")
public class CommentAdminController
{
    @Autowired
    private CommentService commentService;

    @RequestMapping("/list")
    public String listComment(String page, String rows, String state,
                              HttpServletResponse response) throws Exception
    {
        int start = Integer.parseInt(page) - 1;
        int size = Integer.parseInt(rows);

        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", size);
        map.put("state", state);
        List<Comment> list = commentService.listComment(map);
        long total = commentService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", list);
        result.put("total", total);

        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }

    @RequestMapping("/delete")
    public String deleteComment(String ids, HttpServletResponse response) throws Exception
    {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++)
            commentService.deleteComment(Integer.parseInt(idsStr[i]));

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }

    @RequestMapping("/review")
    public String reviewComment(String ids, String state,  HttpServletResponse response) throws Exception
    {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++)
        {
            Comment comment = new Comment();
            comment.setId(Integer.parseInt(idsStr[i]));
            comment.setState(Integer.parseInt(state));
            commentService.updateComment(comment);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }
}
