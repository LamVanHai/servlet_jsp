package com.lamvanhai.listerner;

import com.lamvanhai.beans.BeanFactory;
import com.lamvanhai.cache.Cache;
import com.lamvanhai.cache.CacheImpl;
import com.lamvanhai.common.Config;
import com.lamvanhai.model.response.user.UserResponse;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ApplicationListener implements ServletContextListener {

    public static Cache<String, UserResponse> userCache = null;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.userCache = new CacheImpl<>();

        Properties psp = new Properties();
        try {
            psp.load(new FileReader("E:\\Java05\\shop-02-master\\src\\main\\resources\\DatabaseInfo.properties"));
            Config.url = psp.getProperty("datasource.url");
            Config.user = psp.getProperty("datasource.user");
            Config.password = psp.getProperty("datasource.password");
            Config.connectionTimeOut = Integer.parseInt(psp.getProperty("datasource.connectionTimeOut"));
            Config.driverClassName = psp.getProperty("datasource.driverClassName");
            Config.connetionLifeTime = Integer.parseInt(psp.getProperty("datasource.connectionLifeTime"));
            Config.maxPoolSize = Integer.parseInt(psp.getProperty("datasource.maxPoolSize"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BeanFactory beanFactory = new BeanFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

