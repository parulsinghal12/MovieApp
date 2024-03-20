package com.example.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.presentation.R

@Composable
fun CustomImageView(
    modifier: Modifier = Modifier,
    data: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale,
    placeholder: Int = R.drawable.baseline_img_placeholder, //placeholder image
    error: Int = R.drawable.baseline_error_outline_24 // error image
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .placeholder(placeholder)
            .error(error)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}