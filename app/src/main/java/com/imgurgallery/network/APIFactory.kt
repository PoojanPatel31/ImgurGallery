package com.imgurgallery.network

import com.imgurgallery.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIFactory {

    /**
     * Prepare RestAPI instance.
     */
    val restApi: RestAPI = Retrofit.Builder()
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(RestAPI::class.java)
        get() = field


    /**
     * Prepare a OkHttpClient with request/response interceptor.
     */
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(HTTPInterceptor())
            .build()
    }
}