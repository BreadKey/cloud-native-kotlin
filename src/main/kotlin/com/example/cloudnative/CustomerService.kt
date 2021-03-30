package com.example.cloudnative

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class CustomerService(private val jdbcTemplate: JdbcTemplate) {
    init {
        jdbcTemplate.execute("insert into Customer (id, email) values (0, '2breadkey@gmail.com')")
    }

    fun findAll(): List<Customer> = jdbcTemplate.query("select * from Customer") { result, _ ->
        Customer(
            result.getLong("id"),
            result.getString("email")
        )
    }
}