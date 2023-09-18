package com.imgurgallery.models

data class GalleryResponse(
    val data: List<GalleryItem> = emptyList(),
    val success: Boolean = false,
    val status: Int = 404
)
