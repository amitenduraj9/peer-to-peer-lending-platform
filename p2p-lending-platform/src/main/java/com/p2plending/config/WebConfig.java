package com.p2plending.config;

import com.p2plending.filter.AuthFilter;
import com.p2plending.servlet.PingServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    /** Registers the raw HttpServlet (Module 1: Servlets) at /ping */
    @Bean
    public ServletRegistrationBean<PingServlet> pingServletRegistration() {
        return new ServletRegistrationBean<>(new PingServlet(), "/ping");
    }

    /** Registers the session-check Filter for protected dashboard URLs */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthFilter());
        bean.addUrlPatterns("/borrower/*", "/lender/*", "/admin/*");
        bean.setOrder(1);
        return bean;
    }
}
