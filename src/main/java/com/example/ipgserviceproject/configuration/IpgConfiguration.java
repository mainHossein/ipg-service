package com.example.ipgserviceproject.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class IpgConfiguration {

    @Value("${user.service.base-url}")
    private String userServiceUrl;

    @Bean("user-service")
    public RestClient.Builder userServiceRestClientBuilder(RestTemplateBuilder restTemplateBuilder) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(userServiceUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(factory);
        return RestClient.builder(restTemplate);
    }
}
