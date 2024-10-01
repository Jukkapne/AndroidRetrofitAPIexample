package com.example.myapiexampleapp.model

data class ApiResponse(
    val statusCode: Int,
    val body: List<ImageItem>,
    val error: String
)
