package com.example.cloudnative

import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
data class Customer(
    @Id
    @GeneratedValue
    val id: Long = 0, val email: String)