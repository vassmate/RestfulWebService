package dbhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ApplicationRunner {
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/users").allowedOrigins("http://127.0.0.1:8080");
                registry.addMapping("/users").allowedOrigins("http://localhost:63342");
                registry.addMapping("/delete").allowedOrigins("http://127.0.0.1:8080");
                registry.addMapping("/delete").allowedOrigins("http://localhost:63342");
                registry.addMapping("/add_user").allowedOrigins("http://127.0.0.1:8080");
                registry.addMapping("/add_user").allowedOrigins("http://localhost:63342");
            }
        };
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}
}
