package com.example.myapiexampleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.myapiexampleapp.network.ApiService
import com.example.myapiexampleapp.network.ApiRepository
import com.example.myapiexampleapp.ui.screens.ImageScreen
import com.example.myapiexampleapp.viewModel.ApiViewModel
import com.example.myapiexampleapp.viewModel.ApiViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// MainActivity class
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // API key for authentication
        val apiKey = "2ef300b1-2794-424c-880d-e136f9d73280"

        // OkHttpClient with an interceptor to add the API key to the headers
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                // Add the API key to the request headers
                val request = originalRequest.newBuilder()
                    .addHeader("X-API-KEY", apiKey)
                    .build()

                // Log the request details
                Log.d("OkHttp", "Sending request to URL: ${request.url}")
                Log.d("OkHttp", "Request method: ${request.method}")
                Log.d("OkHttp", "Request headers: ${request.headers}")

                // If the request has a body, log it
                request.body?.let { requestBody ->
                    val buffer = okio.Buffer()
                    requestBody.writeTo(buffer)
                    val charset = requestBody.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
                    Log.d("OkHttp", "Request body: ${buffer.readString(charset)}")
                }

                // Proceed with the request and capture the response
                val response = chain.proceed(request)

                // Log the response details
                Log.d("OkHttp", "Received response from URL: ${response.request.url}")
                Log.d("OkHttp", "Response code: ${response.code}")
                Log.d("OkHttp", "Response message: ${response.message}")
                Log.d("OkHttp", "Response headers: ${response.headers}")

                // To read the response body, we need to make a copy
                val responseBody = response.body
                val source = responseBody?.source()
                source?.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source?.buffer

                val charset = responseBody?.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
                val bodyString = buffer?.clone()?.readString(charset)

                // Log the response body
                Log.d("OkHttp", "Response body: $bodyString")

                // Return the original response
                response
            }
            .build()


        // Retrofit instance for making API calls
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jwstapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of ApiService using Retrofit
        val apiService = retrofit.create(ApiService::class.java)

        // Create an instance of ApiRepository
        val repository = ApiRepository(apiService)

        // Create an instance of ApiViewModelFactory
        val viewModelFactory = ApiViewModelFactory(repository)

        // Create an instance of ApiViewModel
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ApiViewModel::class.java)

        // Set the content of the activity
        setContent {
            // Call the ImageScreen composable function
            ImageScreen(viewModel)
        }
    }
}
