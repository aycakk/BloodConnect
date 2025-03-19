package com.softwarengineering.bloodconnect.service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // 📌 Yakındaki hastaneler ve kan bağış merkezlerini getir
    @GET("place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String, // "hospital|blood_donation_center"
        @Query("key") apiKey: String
    ): Call<JsonObject>

    // 📌 Adresi koordinata çevir (Geocoding API)
    @GET("geocode/json")
    fun getGeocode(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Call<JsonObject>

    // 📌 Kullanıcının konumuna en hızlı rotayı getir (Routes API)
    @GET("directions/json")
    fun getRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String, // "driving", "walking", "transit"
        @Query("key") apiKey: String
    ): Call<JsonObject>

    // 📌 Kullanıcının en yakın yol üzerinde olup olmadığını kontrol et (Roads API)
    @GET("snapToRoads")
    fun snapToRoad(
        @Query("path") path: String,
        @Query("key") apiKey: String
    ): Call<JsonObject>
}
