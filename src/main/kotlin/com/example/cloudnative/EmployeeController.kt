package com.example.cloudnative

import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(private val repository: EmployeeRepository, private val assembler: EmployeeModelAssembler) {
    @GetMapping("/employees")
    fun all(): List<Employee> = repository.findAll()

    @PostMapping("/employees")
    fun newEmployee(@RequestBody newEmployee: Employee) = repository.save(newEmployee)

    @GetMapping("/employees/{id}")
    fun one(@PathVariable id: Long): Employee = repository.findById(id).orElseThrow { EmployeeNotFoundException(id) }

    @PutMapping("/employees/{id}")
    fun replaceEmployee(@RequestBody newEmployee: Employee, @PathVariable id: Long): Employee =
        repository.findById(id).map { employee ->
            employee.name = newEmployee.name
            employee.role = newEmployee.role
            repository.save(employee)
        }.orElseGet {
            newEmployee.id = id
            repository.save(newEmployee)
        }

    @DeleteMapping("/employees/{id}")
    fun deleteEmployee(@PathVariable id: Long) = repository.deleteById(id)
}