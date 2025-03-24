package com.ttn.blog.core.models;
import com.ttn.blog.core.services.PublishedBlogsService;
import com.ttn.blog.core.services.PublishedBlogs;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import java.util.*;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = PublishedListofBlogs.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PublishedListofBlogsImpl implements PublishedListofBlogs {

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    private SlingHttpServletRequest request;

//  We are using this service to get data of Child Pages based on the limit parameter,that we are getting
//  from configuration
    @OSGiService
    private PublishedBlogsService publishedBlogsService;

//  In this service we have used PublishedBlogsConfiguration so we are using this service to get the
//  no of blogs that we have set in configuartion through system/console/configMgr.
    @OSGiService
    private PublishedBlogs publishedBlogs;

    @Override
    public List<Map<String, String>> getBlogs() {
      String monthParam = request.getParameter("month");
        int limit = publishedBlogs.noOfBlogs();
        return publishedBlogsService.getPublishedBlogs(currentPage, monthParam, limit);
    }
}