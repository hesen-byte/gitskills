package com.hesen.service.impl;

import com.hesen.entity.Blog;
import com.hesen.entity.BlogType;
import com.hesen.entity.Blogger;
import com.hesen.entity.Link;
import com.hesen.service.BlogService;
import com.hesen.service.BlogTypeService;
import com.hesen.service.BloggerService;
import com.hesen.service.LinkService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 实现ApplicationContextAware接口，Spring会自动注入Spring上下文
 *
 * 这个类的主要作用是在项目初始化时，从数据库查到相应的数据放入ServletContext对象中
 * 用于前端主页面mainTemplate的显示
 */
@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware
{
    //由于系统刚刚启动，此时还不能开启注解扫描，所以必须使用getBean方法获得service
    private static ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        ServletContext servletContext = servletContextEvent.getServletContext();

        /**获得博客类别列表*/
        BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
        List<BlogType> blogTypeList = blogTypeService.countList();
        servletContext.setAttribute("blogTypeCountList", blogTypeList);

        /**获得博主信息*/
        BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
        Blogger blogger = bloggerService.findBlogger();
        blogger.setPassword(null);
        servletContext.setAttribute("blogger", blogger);

        /**获得按年月分类的博客数量*/
        BlogService blogService = (BlogService) applicationContext.getBean("blogService");
        List<Blog> blogCountList = blogService.countList();
        servletContext.setAttribute("blogCountList", blogCountList);

        /**获得友情链接列表*/
        LinkService linkService = (LinkService) applicationContext.getBean("linkService");
        List<Link> linkList = linkService.listLink(null);
        servletContext.setAttribute("linkList", linkList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
