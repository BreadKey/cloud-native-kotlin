package com.example.cloudnative

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
class KotlinApplication

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}

@Entity
data class Cat(
        val name: String,
        @Id
        @GeneratedValue
        private var id: Long = 0
) {
    fun getId() = id
    override fun toString(): String = "Cat{id=$id, name='$name'}"
}

@RepositoryRestResource
interface CatRepository: JpaRepository<Cat, Long>