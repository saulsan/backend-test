package com.neology.parking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.h2.server.web.WebServlet;
import org.springframework.context.annotation.Bean;

@Configuration
@Profile("!prod") // Solo activa en ambientes que no sean producción
public class H2Config {

    @Bean
    public ServletRegistrationBean<WebServlet> h2ServletRegistration() {
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet());
        registration.addUrlMappings("/h2-console/*");
        return registration;
    }
}
