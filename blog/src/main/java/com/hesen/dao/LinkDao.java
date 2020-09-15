package com.hesen.dao;

import com.hesen.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LinkDao
{
    public Link findLinkById(Integer id);

    public List<Link> listLink(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer addLink(Link link);

    public Integer deleteLink(Integer id);

    public Integer updateLink(Link link);

}
