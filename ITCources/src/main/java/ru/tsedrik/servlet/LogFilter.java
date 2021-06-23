package ru.tsedrik.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init logFilter");
    }

    @Override
    public void destroy() {
        logger.info("destroy logFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String uri = "Requested uri :: " + req.getRequestURI();
        logger.info(uri);
        String method = "Requested method :: " + req.getMethod();
        logger.info(method);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
