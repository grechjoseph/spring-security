package com.jg.springsecurity.config;

import com.jg.springsecurity.filter.FilterOne;
import com.jg.springsecurity.filter.FilterTwo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<FilterOne> filterOneRegistrationBean() {
        final FilterRegistrationBean<FilterOne> customFilter = new FilterRegistrationBean<>();
        customFilter.setFilter(new FilterOne());
        // customFilter.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return customFilter;
    }

    @Bean
    public FilterRegistrationBean<FilterTwo> filterTwoRegistrationBean() {
        final FilterRegistrationBean<FilterTwo> customFilter = new FilterRegistrationBean<>();
        customFilter.setFilter(new FilterTwo());
        // customFilter.setOrder(Ordered.LOWEST_PRECEDENCE);
        return customFilter;
    }

}
