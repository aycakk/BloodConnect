package com.softwarengineering.bloodconnect.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.HospitalApiModel
import com.softwarengineering.bloodconnect.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapVM(application: Application) : AndroidViewModel(application) {

    private val _donors = MutableLiveData<List<Donor>>()
    val donors: LiveData<List<Donor>> = _donors

    private val _hospitalResults = MutableLiveData<List<HospitalApiModel>>()
    val hospitalResults: LiveData<List<HospitalApiModel>> = _hospitalResults

    private val _bloodCenters = MutableLiveData<List<HospitalApiModel>>()
    val bloodCenters: LiveData<List<HospitalApiModel>> = _bloodCenters

    fun fetchNearbyHospitals(location: LatLng, apiKey: String) {
        val loc = "${location.latitude},${location.longitude}"
        ApiClient.apiService.getNearbyPlaces(loc, 10000, "hospital", apiKey)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val results = response.body()?.getAsJsonArray("results")
                        val hospitals = results?.map { place ->
                            val obj = place.asJsonObject
                            val name = obj.get("name").asString
                            val geometry = obj.getAsJsonObject("geometry").getAsJsonObject("location")
                            val lat = geometry.get("lat").asDouble
                            val lng = geometry.get("lng").asDouble
                            val address = obj.get("vicinity")?.asString ?: ""
                            val placeId = obj.get("place_id")?.asString ?: ""

                            HospitalApiModel(
                                name = name,
                                latitude = lat,
                                longitude = lng,
                                address = address,
                                placeId = placeId
                            )
                        } ?: emptyList()

                        _hospitalResults.postValue(hospitals)
                    } else {
                        Log.e("API", "Yanıt başarısız: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("API", "Hata: ${t.message}")
                }
            })
    }

    fun fetchPhoneNumber(hospital: HospitalApiModel, apiKey: String, onResult: (String) -> Unit) {
        ApiClient.apiService.getPlaceDetails(hospital.placeId, "formatted_phone_number", apiKey)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val phone = response.body()
                            ?.getAsJsonObject("result")
                            ?.get("formatted_phone_number")
                            ?.asString ?: ""
                        onResult(phone)
                    } else {
                        onResult("")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    onResult("")
                }
            })
    }

    fun fetchNearbyBloodCenters(location: LatLng, apiKey: String) {
        val loc = "${location.latitude},${location.longitude}"
        ApiClient.apiService.getNearbyByKeyword(loc, 5000, "blood donation", apiKey)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val results = response.body()?.getAsJsonArray("results")
                        val centers = results?.map { place ->
                            val obj = place.asJsonObject
                            val name = obj.get("name").asString
                            val lat = obj.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").asDouble
                            val lng = obj.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").asDouble
                            val address = obj.get("vicinity")?.asString ?: ""
                            val placeId = obj.get("place_id")?.asString ?: ""

                            HospitalApiModel(
                                name = name,
                                latitude = lat,
                                longitude = lng,
                                address = address,
                                phone = "",
                                placeId = placeId
                            )
                        } ?: emptyList()

                        _bloodCenters.postValue(centers)
                    } else {
                        Log.e("API_BLOOD", "Yanıt başarısız: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("API_BLOOD", "Hata: ${t.message}")
                }
            })
    }


    fun requestFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Token alınamadı!", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "Firebase Token: $token")
        }
    }

}
