package com.softwarengineering.bloodconnect.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.ActivityMapsBinding
import com.softwarengineering.bloodconnect.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        getMyLocation()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val myLocation = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))
                    getNearbyBloodCenters(myLocation)
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun getNearbyBloodCenters(userLocation: LatLng) {
        val apiKey = getString(R.string.google_maps_key)
        val location = "${userLocation.latitude},${userLocation.longitude}"
        val radius = 5000 // 5km search radius

        ApiClient.apiService.getNearbyPlaces(location, radius, "blood donation center", apiKey)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val results = response.body()?.getAsJsonArray("results")
                        results?.forEach { place ->
                            val placeObj = place.asJsonObject
                            val name = placeObj.get("name").asString
                            val lat = placeObj.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").asDouble
                            val lng = placeObj.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").asDouble

                            val location = LatLng(lat, lng)
                            val markerOptions = MarkerOptions().position(location).title(name)
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                            mMap.addMarker(markerOptions)
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(this@MapsActivity, "API Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
