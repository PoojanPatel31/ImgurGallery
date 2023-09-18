package com.imgurgallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imgurgallery.models.GalleryItem
import com.imgurgallery.models.ImageTypeEnum
import com.imgurgallery.network.RestAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel(private val restAPI: RestAPI) : ViewModel() {

    val galleryList = MutableLiveData<List<GalleryItem>>()
    private var job: Job? = null

    /**
     * Fetch Imgur images in background thread and show the images in main thread.
     */
    fun fetchGallery() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = restAPI.getGallery()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    galleryList.value = sortByDate(filterResponse(response.body()?.data))
                }
            }
        }

    }

    /**
     * Sort the list of images by latest to oldest.
     */
    private fun sortByDate(galleryItems: List<GalleryItem>?): List<GalleryItem> =
        galleryItems?.sortedByDescending {
            it.datetime
        } ?: emptyList()

    /**
     * Filter out the images from the response which has a non-image type.
     */
    private fun filterResponse(galleryItems: List<GalleryItem>?): List<GalleryItem> =
        galleryItems?.filter {
            it.images.isNotEmpty() || it.images.any { image ->
                ImageTypeEnum.IMAGE.type.equals(
                    image.type,
                    true
                )
            }
        } ?: emptyList()

    /**
     * Cancel the job when view mode is cleared to avoid any memory leak or crash.
     */
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}