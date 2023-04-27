package com.example.stepik.model

data class ApiResponse(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)
