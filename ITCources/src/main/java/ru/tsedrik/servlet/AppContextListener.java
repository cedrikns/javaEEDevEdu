package ru.tsedrik.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.warning("Context Listener was initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.warning("Context Listener was destroyed");
    }

}
