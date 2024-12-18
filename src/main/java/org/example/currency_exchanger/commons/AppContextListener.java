package org.example.currency_exchanger.commons;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.createPool("jdbc:sqlite:demo.db", "", "", 10);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
