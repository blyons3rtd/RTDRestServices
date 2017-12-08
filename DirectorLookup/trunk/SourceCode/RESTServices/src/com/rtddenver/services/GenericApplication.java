package com.rtddenver.services;

import com.rtddenver.services.DirectorLookup;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
public class GenericApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        
        Set<Class<?>> classes = new HashSet<Class<?>>();

        // Register root resources.
        classes.add(DirectorLookup.class);

        // Register provider classes.

        return classes;
    }
}
