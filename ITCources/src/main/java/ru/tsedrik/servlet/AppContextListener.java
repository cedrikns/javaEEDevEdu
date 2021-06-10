package ru.tsedrik.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("AppContext was initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("AppContext was destroyed successfully.");
    }

}
