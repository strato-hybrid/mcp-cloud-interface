package kr.strato.cloudinterface.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
public class RestTemplateGenerator {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static RestTemplate getInstance(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000)).build();


        return restTemplate;
    }
}
