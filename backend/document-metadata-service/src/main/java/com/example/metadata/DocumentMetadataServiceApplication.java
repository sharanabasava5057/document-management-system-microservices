package com.example.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocumentMetadataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentMetadataServiceApplication.class, args);
    }
}
