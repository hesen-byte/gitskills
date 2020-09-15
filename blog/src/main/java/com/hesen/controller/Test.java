package com.hesen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blogger")
public class Test
{
    @RequestMapping("/test")
    public String test()
    {
        System.out.println("进入test方法了");
        return null;
    }
}
