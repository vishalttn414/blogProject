package com.ttn.blog.core.models;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = Resource.class, adapters = HeaderModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderModelImpl implements HeaderModel {

    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    @Default(values = "Bootcamp Website")
    private String websiteName;

    @SlingObject
    private Resource componentResources;

    @Override
    public String getLogoPath() {
        return logoPath;
    }

    @Override
    public String getWebsiteName() {
        return websiteName;
    }

    @Override
    public List<Map<String, String>> getMenuItems() {
        List<Map<String, String>> menuItemsList = new ArrayList<>();
        Resource menuItems = componentResources.getChild("actions");

        if (menuItems != null) {
            for (Resource item : menuItems.getChildren()) {
                Map<String, String> menuItemMap = new HashMap<>();
                menuItemMap.put("Title", item.getValueMap().get("title", String.class));
                menuItemMap.put("Link", item.getValueMap().get("link", String.class));
                menuItemsList.add(menuItemMap);
            }
        }
        return menuItemsList;
    }
}
