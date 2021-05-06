package ru.tsedrik.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class LogFilter implements Filter {

    private Logger logger = Logger.getLogger(LogFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.warning("init logFilter");
    }

    @Override
    public void destroy() {
        logger.warning("destroy logFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String uri = "Requested uri :: " + req.getRequestURI();
        logger.warning(uri);
        String method = "Requested method :: " + req.getMethod();
        logger.warning(method);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
