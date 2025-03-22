package com.example.ecommercebasic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebHandler implements WebMvcConfigurer {
    @Value("${upload.file.dir}")
    private String uploadFileDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** yolunu /var/www/uploads/ dizinine yönlendir
        System.out.println("addResourceHandlers");
        registry.addResourceHandler("/api/v1/upload/**")
                .addResourceLocations("file:/var/www/upload/ecommerce/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }
}
