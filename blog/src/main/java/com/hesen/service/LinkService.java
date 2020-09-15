package com.hesen.service;

import com.hesen.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkService
{
    public Link findLinkById(Integer id);

    public List<Link> listLink(Map<String, Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer addLink(Link link);

    public Integer deleteLink(Integer id);

    public Integer updateLink(Link link);
}
