package com.hesen.controller.admin;

import com.google.gson.Gson;
import com.hesen.entity.Blogger;
import com.hesen.service.BloggerService;
import com.hesen.utils.DateUtils;
import com.hesen.utils.Encrypt;
import com.hesen.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin/blogger")
@Controller
public class BloggerAdminController
{
    @Autowired
    private BloggerService bloggerService;

    @RequestMapping(value = "/save", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String saveBlogger(@RequestParam("imageFile") MultipartFile imageFile, Blogger blogger,
                              HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (!imageFile.isEmpty())
        {
            //获取当前项目的绝对路径
            String filePath = request.getServletContext().getRealPath("/static/userImages/");
            String imageName = DateUtils.getCurrentDateStr() + "."
                    + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath, imageName));
            blogger.setImageName(imageName);
        }

        StringBuilder result = new StringBuilder();
        int rtValue = bloggerService.updateBlogger(blogger);
        if (rtValue > 0) result.append("<script language='javascript'>alert('修改成功');</script>");
        else result.append("<script language='javascript'>alert('修改失败');</script>");

        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/modifyPassword", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String ModifyPassword(String id, String newPassword, HttpServletResponse response) throws Exception
    {
        Blogger blogger = new Blogger();
        blogger.setId(Integer.parseInt(id));
        blogger.setPassword(Encrypt.md5(newPassword, "hesen"));
        int rtValue = bloggerService.updateBlogger(blogger);
        Map<String, Object> result = new HashMap<>();
        if (rtValue > 0) result.put("success", true);
        else result.put("success", false);
        return new Gson().toJson(result);
    }

    @RequestMapping("/logout")
    public String logout()
    {
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
