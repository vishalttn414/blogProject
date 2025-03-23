package com.ttn.blog.core.services;
import com.day.cq.wcm.api.Page;
import org.osgi.service.component.annotations.Component;
import java.text.SimpleDateFormat;
import java.util.*;

// This is a generic service which is used whenever we want to fetch data of child pages based on month
//  and limit.

@Component(service = PublishedBlogsService.class,immediate = true)
public class PublishedBlogsServiceImpl implements PublishedBlogsService {

    @Override
    public List<Map<String, String>> getPublishedBlogs(Page currentPage, String monthFilter, int limit) {
        List<Map<String, String>> blogs = new ArrayList<>();
        Iterator<Page> childPages = currentPage.listChildren();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM yyyy");

        while (limit > 0 && childPages.hasNext()) {
            Page childPage = childPages.next();
            Date createdDate = childPage.getProperties().get("jcr:created", Date.class);
            String formattedDate = sdf.format(createdDate);

            if (monthFilter!=null){
                if(!formattedDate.equals(monthFilter)){
                    continue;
                }
            }

            Map<String, String> blog = new HashMap<>();
            blog.put("Title", childPage.getTitle());
            blog.put("Date", sdf2.format(createdDate));
            blog.put("Img", getImagePath(childPage));
            blog.put("Link", childPage.getPath() + ".html");
            blog.put("SubHeading", childPage.getDescription() != null ? childPage.getDescription() : "No Description");

            blogs.add(blog);
            limit--;
        }
        return blogs;
    }

    private String getImagePath(Page childPage) {
        return childPage.getPath() + "/jcr:content/root/responsivegrid/image/file";
    }
}