package com.example.myapiexampleapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapiexampleapp.model.ImageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.myapiexampleapp.network.ApiRepository
import kotlinx.coroutines.flow.asStateFlow


// ViewModel class that holds the UI data
class ApiViewModel(private val repository: ApiRepository) : ViewModel() {

    // MutableStateFlow to hold the list of images
    private val _images = MutableStateFlow<List<ImageItem>>(emptyList())

    // Publicly exposed StateFlow for the UI to observe
    val images: StateFlow<List<ImageItem>> = _images

    // MutableStateFlow to hold the list of image URLs
    private val _imageUrls = MutableStateFlow<List<String>>(emptyList())
    // Publicly exposed StateFlow for the UI to observe
    val imageUrls: StateFlow<List<String>> = _imageUrls

//    init {
//        Log.d("ApiViewModel", "ViewModel initialized")
//        fetchImageUrls()
//    }

    // Function to fetch images from the repository
     fun fetchImageUrls() {
        viewModelScope.launch {
            try {
                val urls = repository.fetchImageLocations(page = 1, perPage = 10)
                _imageUrls.value = urls
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ApiViewModel", "Error fetching image URLs", e)
            }
        }
    }
}
