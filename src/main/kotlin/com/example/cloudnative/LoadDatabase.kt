package com.example.cloudnative

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDatabase {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun initDatabase(repository: EmployeeRepository) = CommandLineRunner { _ ->
        log.info("Preloading " + repository.save(Employee("Bilbo Baggins", "burglar")))
        log.info("Preloading " + repository.save(Employee("Frodo Baggins", "thief")))
    }
}