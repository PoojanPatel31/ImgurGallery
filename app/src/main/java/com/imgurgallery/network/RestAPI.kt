package com.imgurgallery.network

import com.imgurgallery.models.GalleryResponse
import retrofit2.Response
import retrofit2.http.GET

interface RestAPI {

    /**
     * An endpoint to get list of top images of week.
     */
    @GET("3/gallery/top/time/0/week")
    suspend fun getGallery(): Response<GalleryResponse>

}