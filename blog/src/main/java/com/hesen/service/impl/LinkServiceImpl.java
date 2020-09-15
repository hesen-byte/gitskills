package com.hesen.service.impl;

import com.hesen.dao.LinkDao;
import com.hesen.entity.Link;
import com.hesen.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements LinkService
{
    @Autowired
    private LinkDao linkDao;


    @Override
    public Link findLinkById(Integer id)
    {
        return linkDao.findLinkById(id);
    }

    @Override
    public List<Link> listLink(Map<String, Object> map)
    {
        return linkDao.listLink(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map)
    {
        return linkDao.getTotal(map);
    }

    @Override
    public Integer addLink(Link link)
    {
        return linkDao.addLink(link);
    }

    @Override
    public Integer deleteLink(Integer id)
    {
        return linkDao.deleteLink(id);
    }

    @Override
    public Integer updateLink(Link link)
    {
        return linkDao.updateLink(link);
    }
}
