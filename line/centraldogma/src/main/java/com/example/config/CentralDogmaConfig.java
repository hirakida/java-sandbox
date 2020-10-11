package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.centraldogma.client.spring.CentralDogmaClientAutoConfiguration.ForCentralDogma;

@Configuration
public class CentralDogmaConfig {

    @Bean
    @ForCentralDogma
    public ClientFactory clientFactory() {
        return ClientFactory.builder()
                            .maxNumEventLoopsPerEndpoint(5)
                            .build();
    }
}
