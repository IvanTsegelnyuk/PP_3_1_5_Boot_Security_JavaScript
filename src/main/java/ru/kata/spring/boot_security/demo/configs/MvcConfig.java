package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
         registry.addViewController("/api").setViewName("users_new");
        registry.addViewController("/api/users").setViewName("index");
//        registry.addViewController("/api/test").setViewName("user");
    }
}
