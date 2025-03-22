package com.ttn.blog.core.models;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
        adapters = FooterModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModelImpl implements FooterModel {

    @ValueMapValue
    private String homePageLink;

    @ValueMapValue
    private String blogsLink;

    @Override
    public String getHomePageLink() {
        return homePageLink;
    }

    @Override
    public String getBlogsLink() {
        return blogsLink;
    }
}