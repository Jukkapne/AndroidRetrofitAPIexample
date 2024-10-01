package com.example.myapiexampleapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapiexampleapp.network.ApiRepository

// ViewModelFactory to create ApiViewModel instances
class ApiViewModelFactory(private val repository: ApiRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the modelClass is ApiViewModel
        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Return a new instance of ApiViewModel
            return ApiViewModel(repository) as T
        }
        // Throw an exception if the ViewModel is unknown
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
