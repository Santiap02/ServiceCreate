package com.example.ApplicationConfig;

import Model.Cliente;
import Util.ClienteMapper;
import domain.ClienteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Slf4j
@Import(SecurityConfig.class)
@ComponentScan(basePackages = {"rest","Model","business", "repository", "com.example.ApplicationConfig", "Util"})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"repository"})
@EnableMongoRepositories(basePackages = {"repository"})
@SpringBootApplication
public class ServiceCreateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCreateApplication.class, args);
    }
    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
    @Bean
    public ClienteDto run(ClienteMapper clienteMapper) {
        log.info("corriendo runtime");
        var a =(clienteMapper.clientToClienteDto(new Cliente(12,"aa", "a", "a", 12, "med")));
        return  clienteMapper.clientToClienteDto(new Cliente());
    }
}
