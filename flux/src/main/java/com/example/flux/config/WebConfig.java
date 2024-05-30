package com.example.flux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("CORS setup configuration started");
        registry.addMapping("/**")
                .allowedOrigins("https://food-automation-u-i-9gobko.flutterflow.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
        registry.addMapping("/**")
                .allowedOrigins("https://foodautomation-backend.azurewebsites.net")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
        registry.addMapping("/**")
                .allowedOrigins("https://foodautomation-backend-apim.azure-api.net")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
