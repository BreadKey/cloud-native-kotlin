package com.example.cloudnative

class EmployeeNotFoundException(id: Long) : RuntimeException("Could not find employee $id")