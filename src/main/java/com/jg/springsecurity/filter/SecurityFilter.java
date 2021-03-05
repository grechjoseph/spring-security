package com.jg.springsecurity.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        log.info("Running {}.", this.getClass().getSimpleName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
