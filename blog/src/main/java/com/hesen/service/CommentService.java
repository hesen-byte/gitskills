package com.hesen.service;

import com.hesen.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService
{
    public Integer addComment(Comment comment);

    public Integer updateComment(Comment comment);

    public List<Comment> listComment(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer deleteComment(Integer id);
}
