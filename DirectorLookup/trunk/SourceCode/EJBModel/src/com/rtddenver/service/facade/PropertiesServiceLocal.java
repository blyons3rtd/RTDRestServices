package com.rtddenver.service.facade;

import java.util.Properties;

import javax.ejb.Local;

@Local
public interface PropertiesServiceLocal {
    public Properties getProperties();
}
