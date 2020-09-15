package com.hesen.service.impl;

import com.hesen.dao.CommentDao;
import com.hesen.entity.Comment;
import com.hesen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CommentService")
public class CommentServiceImpl implements CommentService
{
    @Autowired
    private CommentDao commentDao;

    @Override
    public Integer addComment(Comment comment)
    {
        return commentDao.addComment(comment);
    }

    @Override
    public Integer updateComment(Comment comment)
    {
        return commentDao.updateComment(comment);
    }

    @Override
    public List<Comment> listComment(Map<String, Object> map)
    {
        return commentDao.listComment(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map)
    {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer deleteComment(Integer id)
    {
        return commentDao.deleteComment(id);
    }
}
