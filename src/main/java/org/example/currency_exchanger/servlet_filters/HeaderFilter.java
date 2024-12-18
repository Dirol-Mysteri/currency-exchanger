package org.example.currency_exchanger.servlet_filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class HeaderFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра (если необходимо)
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");

        // Продолжение цепочки фильтров
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        // Очистка ресурсов (если необходимо)
    }
}
