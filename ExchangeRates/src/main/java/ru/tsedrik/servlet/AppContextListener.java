package ru.tsedrik.servlet;

import ru.tsedrik.entity.Currency;
import ru.tsedrik.entity.Rate;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Map<LocalDate, List<Rate>> exchangeRates = generateExchangeRates();
        servletContext.setAttribute("exchangeRates", exchangeRates);
        logger.warning("Context Listener was initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.warning("Context Listener was destroyed");
    }

    private Map<LocalDate, List<Rate>> generateExchangeRates(){
        Map<LocalDate, List<Rate>> exchangeRates = new HashMap<>();
        Random random = new Random();
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < 11; i++){
            List<Rate> daysData = new ArrayList<>();
            daysData.add(new Rate(Currency.USD, random.nextDouble() * 10));
            daysData.add(new Rate(Currency.EUR, random.nextDouble() * 50));
            daysData.add(new Rate(Currency.GBP, random.nextDouble() * 100));
            exchangeRates.put(localDate, daysData);
            localDate = localDate.minusDays(1);
        }

        return exchangeRates;
    }

}
