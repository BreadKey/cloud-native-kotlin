package com.example.cloudnative

class OrderNotFoundException(id: Long): RuntimeException("Order not found $id")
