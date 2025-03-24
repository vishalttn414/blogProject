package com.ttn.blog.core.models;
import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.adobe.granite.security.user.UserPropertiesService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = HeadingModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeadingModelImpl implements HeadingModel {

    @ScriptVariable
    private Page currentPage;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue(name = "jcr:createdBy")
    private String userId;

    @ValueMapValue(name = "jcr:created")
    private Calendar created;

    @Override
    public String getTitle() {
        return currentPage.getTitle();
    }

    @Override
    public String getAuthorName() {
        String createdBy=getLoggedInUserName();
        return createdBy != null ? createdBy : "Unknown Author";
    }

    @Override
    public String getCreatedDate() {
        if (created != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
            return sdf.format(created.getTime());
        }
        return "No Date Found";
    }

    private String getLoggedInUserName() {
        try {
            UserPropertiesManager upm = resourceResolver.adaptTo(UserPropertiesManager.class);
            if (upm != null) {
                UserProperties userProperties = upm.getUserProperties(userId, UserPropertiesService.PROFILE_PATH);;
                if (userProperties != null) {
                    String fullName = userProperties.getDisplayName();
                    return (fullName != null && !fullName.isEmpty()) ? fullName : userId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }
}
