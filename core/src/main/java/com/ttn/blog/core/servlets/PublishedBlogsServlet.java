package com.ttn.blog.core.servlets;

import com.ttn.blog.core.services.PublishedBlogsService;
import com.ttn.blog.core.services.PublishedBlogs;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class,property = {
        "sling.servlet.resourceTypes=" + "blogProject/components/xfpage",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.selectors=" + "publishedblogsdata",
        "sling.servlet.extensions=" + "json"

})
public class PublishedBlogsServlet extends SlingSafeMethodsServlet {

    @Reference
    private PublishedBlogsService publishedBlogsService;

    @Reference
    private PublishedBlogs publishedBlogs;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(request.getResource());

        if (currentPage != null) {
            String monthParam = request.getParameter("month");
            int limit = publishedBlogs.noOfBlogs();
            List<Map<String, String>> blogs = publishedBlogsService.getPublishedBlogs(currentPage, monthParam, limit);

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(blogs));
        } else {
            response.getWriter().write("[]");
        }
    }
}
