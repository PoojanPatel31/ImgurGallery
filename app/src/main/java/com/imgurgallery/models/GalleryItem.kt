package com.imgurgallery.models

data class GalleryItem(
    val title: String = "",
    val datetime: Long = System.currentTimeMillis(),
    val images: List<GalleryImage> = emptyList()
)
