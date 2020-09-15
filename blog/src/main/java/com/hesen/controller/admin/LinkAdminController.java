package com.hesen.controller.admin;

import com.google.gson.Gson;
import com.hesen.entity.Link;
import com.hesen.service.LinkService;
import com.hesen.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("linkAdminController")
@RequestMapping("/admin/link")
public class LinkAdminController
{
    @Autowired
    private LinkService linkService;

    @RequestMapping("/list")
    public List<Link> listLink(String page, String rows, HttpServletResponse response) throws Exception
    {
        int start = Integer.parseInt(page) - 1;
        int size = Integer.parseInt(rows);

        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("rows", rows);
        List<Link> list = linkService.listLink(map);
        long total = linkService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", list);
        result.put("total", total);
        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }

    @RequestMapping("/save")
    public String saveLink(Link link, HttpServletResponse response) throws Exception
    {
        int rtValue = 0;
        if (link.getId() == null) rtValue = linkService.addLink(link);
        else rtValue = linkService.updateLink(link);

        Map<String, Object> result = new HashMap<>();
        if (rtValue > 0) result.put("success", true);
        else result.put("success", false);

        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }

    @RequestMapping("/delete")
    public String deleteLinks(String ids, HttpServletResponse response) throws Exception
    {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++)
            linkService.deleteLink(Integer.parseInt(idsStr[i]));

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        ResponseUtils.write(response, new Gson().toJson(result));
        return null;
    }
}
