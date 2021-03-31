package com.example.cloudnative

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Employee(
    var firstName: String?,
    var lastName: String?,
    var role: String,
    @Id
    @GeneratedValue
    var id: Long = 0
) {
    var name: String
        get() = "$firstName $lastName"
        set(value) {
            with(value.split(" ")) {
                firstName = first()
                lastName = last()
            }
        }
}