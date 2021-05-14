package ru.tsedrik.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try{
            SessionFactory sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(ru.tsedrik.entity.Student.class)
                    .addAnnotatedClass(ru.tsedrik.entity.Course.class)
                    .buildSessionFactory();
            context.setAttribute("sessionFactory", sessionFactory);
            logger.info("SessionFactory was created successfully.");
        } catch (Exception e){
            logger.severe("Creating of sessionFactory was finished with error: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory sessionFactory = (SessionFactory)sce.getServletContext().getAttribute("sessionFactory");
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
                logger.info("SessionFactory was closed successfully.");
            }
        } catch (Exception e){
            logger.severe("Closing of sessionFactory was finished with error: " + e.getMessage());
        }
    }

}
