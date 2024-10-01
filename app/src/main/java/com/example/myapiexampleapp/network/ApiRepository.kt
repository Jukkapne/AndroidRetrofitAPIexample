package com.example.myapiexampleapp.network


import android.util.Log
import com.example.myapiexampleapp.model.ImageItem;


// Repository class that handles API calls
class ApiRepository(private val apiService: ApiService) {

    suspend fun fetchImageLocations(page: Int, perPage: Int): List<String> {
        Log.d("ApiRepository", "Fetching images from API")

        val response = apiService.getImages(page, perPage)
        Log.d("ApiRepository", "Received ${response.body.size} items")

        // Extract the 'location' field from each ImageItem
        val locations = response.body.map { it.location }
        return locations
    }
}

