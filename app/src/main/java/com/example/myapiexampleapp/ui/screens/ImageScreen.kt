package com.example.myapiexampleapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapiexampleapp.viewModel.ApiViewModel
import coil.compose.AsyncImage
import com.example.myapiexampleapp.model.ImageItem


// Composable function that builds the UI
@Composable
fun ImageScreen(viewModel: ApiViewModel) {
    // Collect the images StateFlow as a state in the UI
    val images by viewModel.images.collectAsState()

    // Build the UI layout
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the image if available
        if (images.isNotEmpty()) {
            // Get the image URL from the first item in the list
            val imageUrl = images[0].location

            // Use Coil to load the image from the URL
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
        } else {
            // If no image is loaded, display a placeholder text
            Text("No Image Loaded")
        }
        // Button to initiate the API call
        Button(onClick = {
            // Call the fetchImages function in the ViewModel
            viewModel.fetchImageUrls()
        }) {
            // Button text
            Text("Fetch Image")
        }
    ImageListScreen(viewModel = viewModel)
    }
}

@Composable
fun ImageListScreen(viewModel: ApiViewModel) {
    val imageUrls by viewModel.imageUrls.collectAsState()

    LazyColumn {
        items(imageUrls) { imageUrl ->
            ImageItemView(imageUrl)
        }
    }
}


@Composable
fun ImageItemView(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )
}




