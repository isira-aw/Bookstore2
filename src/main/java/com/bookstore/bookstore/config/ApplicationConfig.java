package com.bookstore.bookstore.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

import com.bookstore.bookstore.resource.*;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(BookResource.class);
        resources.add(AuthorResource.class);
        resources.add(CustomerResource.class);
        resources.add(CartResource.class);
        resources.add(OrderResource.class);
        return resources;
    }
}
