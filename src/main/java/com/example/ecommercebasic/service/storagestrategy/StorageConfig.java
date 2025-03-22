package com.example.ecommercebasic.service.storagestrategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    @Bean
    public IStorageStrategy localStorageStrategy() {
        return new LocalStorageStrategy();
    }

    @Bean
    public IStorageStrategy awsS3StorageStrategy() {
        return new AWSS3StorageStrategy();
    }
}
