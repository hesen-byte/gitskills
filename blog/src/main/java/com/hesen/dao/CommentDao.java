package com.hesen.dao;

import com.hesen.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentDao
{
    public Integer addComment(Comment comment);

    public Integer updateComment(Comment comment);

    public List<Comment> listComment(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer deleteComment(Integer id);

    public Integer deleteCommentByBlogId(Integer blogId);
}
