package com.ttn.blog.core.models;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        adapters = About.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AboutImpl implements About {

    private static final String FIXED_HEADING = "About";

    @ValueMapValue
    private String about;

    @Override
    public String getAbout() {
        return about;
    }

    @Override
    public String getHeading() {
        return FIXED_HEADING;
    }
}