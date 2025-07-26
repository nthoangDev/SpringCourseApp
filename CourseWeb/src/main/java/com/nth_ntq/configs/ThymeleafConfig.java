

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pc
 */

package com.nth_ntq.configs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class ThymeleafConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public ThymeleafConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver r = new SpringResourceTemplateResolver();
        r.setApplicationContext(this.applicationContext);
        r.setPrefix("/WEB-INF/templates/");
        r.setSuffix(".html");
        r.setCharacterEncoding("UTF-8");
        return r;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine e = new SpringTemplateEngine();
        e.setTemplateResolver(templateResolver());
        return e;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver v = new ThymeleafViewResolver();
        v.setTemplateEngine(templateEngine());
        v.setCharacterEncoding("UTF-8");
        return v;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/resources/", "classpath:/static/");
    }
}
