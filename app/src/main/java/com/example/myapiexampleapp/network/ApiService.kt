package com.example.myapiexampleapp.network

import com.example.myapiexampleapp.model.ApiResponse
import com.example.myapiexampleapp.model.ImageItem
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface defining the API endpoints
interface ApiService {

    @GET("all/type/jpg")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): ApiResponse
}