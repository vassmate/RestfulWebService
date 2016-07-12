package dbhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ApplicationRunner {
	private final String corsURL = "http://localhost:63342";
	
	//CORS configuration to avoid the Access-Control-Allow-Origin errors in certain browsers
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/user").allowedOrigins(corsURL);
                registry.addMapping("/delete_user").allowedOrigins(corsURL);
                registry.addMapping("/add_user").allowedOrigins(corsURL);
                registry.addMapping("/update_user").allowedOrigins(corsURL);
            }
        };
    }
	
	//Start the application
	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}
}
