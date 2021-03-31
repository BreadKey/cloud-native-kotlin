package com.example.cloudnative

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(private val repository: EmployeeRepository, private val assembler: EmployeeModelAssembler) {
    @GetMapping("/employees")
    fun all(): CollectionModel<EntityModel<Employee>> {
        val employees = repository.findAll().map { employee -> assembler.toModel(employee) }

        return CollectionModel.of(
            employees,
            linkTo<EmployeeController> { all() }.withSelfRel()
        )
    }

    @PostMapping("/employees")
    fun newEmployee(@RequestBody newEmployee: Employee): ResponseEntity<Any> {
        newEmployee.id = 0
        val entityModel = assembler.toModel(repository.save(newEmployee))

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    @GetMapping("/employees/{id}")
    fun one(@PathVariable id: Long): EntityModel<Employee> {
        val employee = repository.findById(id).orElseThrow { EmployeeNotFoundException(id) }

        return assembler.toModel(employee)
    }

    @PutMapping("/employees/{id}")
    fun replaceEmployee(@RequestBody newEmployee: Employee, @PathVariable id: Long): ResponseEntity<Any> {
        val updatedEmployee = repository.findById(id).map { employee ->
            employee.name = newEmployee.name
            employee.role = newEmployee.role
            repository.save(employee)
        }.orElseGet {
            newEmployee.id = id
            repository.save(newEmployee)
        }

        val entityModel = assembler.toModel(updatedEmployee)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    @DeleteMapping("/employees/{id}")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<Any> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}