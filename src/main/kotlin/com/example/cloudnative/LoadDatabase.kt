package com.example.cloudnative

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadDatabase {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun initDatabase(employeeRepository: EmployeeRepository, orderRepository: OrderRepository) =
        CommandLineRunner { _ ->
            arrayOf(
                Employee("Bilbo", "Baggins", "burglar"),
                Employee("Frodo", "Baggins", "thief")
            ).forEach { employee ->
                log.info("Preload ${employeeRepository.save(employee)}")
            }

            arrayOf(
                Order("MacBook Pro", Status.COMPLETED),
                Order("iPhone", Status.IN_PROGRESS)
            ).forEach { order ->
                log.info("Preload ${orderRepository.save(order)}")
            }
        }
}