package com.softwarengineering.bloodconnect.viewmodel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MapVM(application: Application) : AndroidViewModel(application) {

    private val _donors = MutableLiveData<List<Donor>>()
    val donors: LiveData<List<Donor>> = _donors

    private val _hospitalResults = MutableLiveData<List<Pair<String, LatLng>>>()
    val hospitalResults: LiveData<List<Pair<String, LatLng>>> = _hospitalResults

    private val _bloodCenters = MutableLiveData<List<Pair<String, LatLng>>>()
    val bloodCenters: LiveData<List<Pair<String, LatLng>>> = _bloodCenters

    /*
    init {
        _donors.value = listOf(
            DonorModel("Ali Yılmaz", "O-", LatLng(41.0082, 28.9784), LocalDate.parse("2023-12-10"), true),
            DonorModel("Ayşe Demir", "A+", LatLng(41.05, 28.99), LocalDate.parse("2024-03-01"), true),
            DonorModel("Mehmet Kılıç", "B+", LatLng(40.99, 28.95), LocalDate.parse("2023-10-01"), false)
        )
    }

     */

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
                            val lat = obj.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").asDouble
                            val lng = obj.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").asDouble
                            name to LatLng(lat, lng)
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
                            name to LatLng(lat, lng)
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
/*
    fun findEligibleDonors(bloodType: String, hospitalLocation: LatLng): List<DonorModel> {
        val currentDate = LocalDate.now()
        return donors.value?.filter {
            it.bloodType == bloodType &&
                    it.available &&
                    it.location.distanceTo(hospitalLocation) < 5000 &&
                    it.lastDonation.isBefore(currentDate.minusDays(90))
        } ?: emptyList()
    }

 */

    private fun LatLng.distanceTo(other: LatLng): Double {
        val earthRadius = 6371000.0
        val dLat = Math.toRadians(other.latitude - this.latitude)
        val dLng = Math.toRadians(other.longitude - this.longitude)
        val a = sin(dLat / 2).pow(2.0) + cos(Math.toRadians(this.latitude)) *
                cos(Math.toRadians(other.latitude)) * sin(dLng / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    fun sendNotification(context: Context, donorName: String, hospitalName: String) {
        val channelId = "emergency_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Emergency", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_blood_drop)
            .setContentTitle("Emergency Blood Call!")
            .setContentText("$donorName, $hospitalName’da acil kana ihtiyaç var.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(donorName.hashCode(), notification)
    }
}
