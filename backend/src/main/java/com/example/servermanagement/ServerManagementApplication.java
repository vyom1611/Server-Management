package com.example.servermanagement;

import com.example.servermanagement.config.RSAKeyProperties;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.model.Status;
import com.example.servermanagement.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class ServerManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepo) {
        return args -> {
            serverRepo.save(new Server(null, "192.168.1.100", "Linux machine", "16 GB", "Personal Laptop", "http://localhost:5432/servers/image/server1.png", Status.SERVER_UP));
            serverRepo.save(new Server(null, "192.168.1.108", "Windows machine", "6 GB", "Work Laptop", "http://localhost:5432/servers/image/server2.png", Status.SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.1.152", "Mac machine", "16 GB", "School Laptop", "http://localhost:5432/servers/image/server3.png", Status.SERVER_UP));
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/servers/list").allowedOrigins("http://localhost:8080", "http://localhost:4200");
            }
        };
    }
}
