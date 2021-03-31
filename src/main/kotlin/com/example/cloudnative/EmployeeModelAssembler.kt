package com.example.cloudnative

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class EmployeeModelAssembler : RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    override fun toModel(employee: Employee): EntityModel<Employee> =
        EntityModel.of(employee, linkTo<EmployeeController> {
            one(employee.id)
        }.withSelfRel(), linkTo<EmployeeController> {
            all()
        }.withRel("employees"))
}