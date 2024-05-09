package com.savants.Pokemon.Cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica CORS a todas las rutas
                .allowedOrigins("*") // Permite solicitudes solo desde http://localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Métodos HTTP permitidos
    }
}
