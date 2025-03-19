package com.softwarengineering.bloodconnect.network

import android.content.Context
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getApiKey(context: Context): String {
        return context.getString(R.string.google_maps_key)
    }

    private const val BASE_URL = "https://maps.googleapis.com/maps/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
