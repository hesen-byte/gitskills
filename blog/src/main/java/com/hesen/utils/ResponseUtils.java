package com.hesen.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils
{
    public static void write(HttpServletResponse response, Object o) throws Exception
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(o);
        out.flush();
        out.close();
    }
}
