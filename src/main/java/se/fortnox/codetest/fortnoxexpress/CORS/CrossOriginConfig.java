package se.fortnox.codetest.fortnoxexpress.CORS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.fortnox.codetest.fortnoxexpress.controller.OrderController;


@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(CrossOriginConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .maxAge(3600);
    }
}
