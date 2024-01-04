package org.example.beertag.configuration;

import org.example.beertag.repositories.BeerRepository;
import org.example.beertag.repositories.BeerRepositoryImpl;
import org.example.beertag.sevices.BeerService;
import org.example.beertag.sevices.BeerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public BeerService beerService(){
        return new BeerServiceImpl(beerRepository());
    }

    @Bean
    public BeerRepository beerRepository(){
        return new BeerRepositoryImpl();
    }
}
