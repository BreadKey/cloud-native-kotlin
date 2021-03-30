package com.example.cloudnative

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerRestController(private val customerService: CustomerService) {
    @GetMapping("/customers")
    fun readAll() = customerService.findAll()
}