package com.ttn.blog.core.services;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

//Service for getting the noOfBlogs that we have set in Configuration

@Component(service = PublishedBlogs.class)
@Designate(ocd = PublishedBlogsConfiguration.class)
public class PublishedBlogsImpl implements PublishedBlogs {

    int blogs;

    @Activate
    @Modified
    public void activate(PublishedBlogsConfiguration config){
        blogs=config.noOfBlogs();
    }

    @Override
    public int noOfBlogs() {
        return blogs;
    }
}
