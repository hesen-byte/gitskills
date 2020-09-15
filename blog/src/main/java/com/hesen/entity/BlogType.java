package com.hesen.entity;

import java.io.Serializable;

public class BlogType implements Serializable
{
    private Integer id;
    private String typeName;
    private String orderNo;
    private Integer blogCount;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Integer getBlogCount()
    {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount)
    {
        this.blogCount = blogCount;
    }
}
