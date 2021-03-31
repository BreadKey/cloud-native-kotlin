package com.example.cloudnative

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Employee(
    var name: String,
    var role: String,
    @Id
    @GeneratedValue
    var id: Long = 0
)