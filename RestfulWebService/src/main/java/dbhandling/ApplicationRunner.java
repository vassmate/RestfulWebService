package dbhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ApplicationRunner {
	
	//CORS configuration to avoid the Access-Control-Allow-Origin errors in certain browsers
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/user").allowedOrigins("http://localhost:63342");
                registry.addMapping("/delete_user").allowedOrigins("http://localhost:63342");
                registry.addMapping("/add_user").allowedOrigins("http://localhost:63342");
                registry.addMapping("/update_user").allowedOrigins("http://localhost:63342");
            }
        };
    }
	
	//Start the application
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}
}
