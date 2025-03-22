package com.ttn.blog.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Published Blogs")
public @interface PublishedBlogsConfiguration {
      @AttributeDefinition(name="please enter the no of Blogs you want to see")
      int noOfBlogs() default 3;
}
