package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.Hospital
import com.softwarengineering.bloodconnect.data.model.HospitalApiModel
import com.softwarengineering.bloodconnect.databinding.FragmentMapBinding
import com.softwarengineering.bloodconnect.service.GeofenceBroadcastReceiver
import com.softwarengineering.bloodconnect.viewmodel.MapVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private val viewModel: MapVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.google_maps_key))
        }

        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        geofencingClient = LocationServices.getGeofencingClient(requireContext())
        requestNotificationPermission()
        viewModel.requestFirebaseToken()

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observeViewModel()

        /*
        binding?.acilKanButonu?.setOnClickListener {
            simulateEmergencyRequest()
        }

         */
    }

    private fun observeViewModel() {
        //hastaneler
        viewModel.hospitalResults.observe(viewLifecycleOwner) { hospitalList ->
            Log.d("MapFragment", "Map'e eklenecek hastane sayısı: ${hospitalList.size}")
            hospitalList.forEach { hospital ->
                val latLng = LatLng(hospital.latitude, hospital.longitude)
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(hospital.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
                marker?.tag = hospital  // 🔗 Tag olarak HospitalApiModel atanıyor
                addGeofence(latLng, hospital.name)
            }
        }
        //kan merkezleri
        viewModel.bloodCenters.observe(viewLifecycleOwner) { centers ->
            centers.forEach { center ->
                val latLng = LatLng(center.latitude, center.longitude)
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(center.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                marker?.tag = center
                addGeofence(latLng, center.name)
            }
        }
    }



        /*
            @RequiresApi(Build.VERSION_CODES.O)
            private fun simulateEmergencyRequest() {
                val hospitalName = "Florence Nightingale Hospital"
                val hospitalLocation = LatLng(41.0080, 28.9790)
                val bloodType = "O-"

                val matched = viewModel.findEligibleDonors(bloodType, hospitalLocation)

                if (matched.isEmpty()) {
                    Toast.makeText(requireContext(), "Uygun donör bulunamadı", Toast.LENGTH_SHORT).show()
                } else {
                    matched.forEach {
                        viewModel.sendNotification(requireContext(), it.name, hospitalName)
                    }
                }
            }

         */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        val myLocation = LatLng(40.9913, 28.8321)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))

        val apiKey = getString(R.string.google_maps_key)
        viewModel.fetchNearbyHospitals(myLocation, apiKey)
        viewModel.fetchNearbyBloodCenters(myLocation, apiKey)

        // Marker tıklamasında bottom sheet aç
        mMap.setOnMarkerClickListener { marker ->
            val hospital = marker.tag as? HospitalApiModel
            if (hospital != null) {
                viewModel.fetchPhoneNumber(hospital, getString(R.string.google_maps_key)) { phone ->
                    val updated = hospital.copy(phone = phone)
                    val latLng = LatLng(updated.latitude, updated.longitude)
                    val bottomSheet = LocationBottomSheet(updated, latLng)
                    bottomSheet.show(parentFragmentManager, bottomSheet.tag)
                }
            }
            true
        }


    }

    private fun addGeofence(location: LatLng, name: String) {
        val geofence = Geofence.Builder()
            .setRequestId(name)
            .setCircularRegion(location.latitude, location.longitude, 500f)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        geofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent())
            .addOnSuccessListener {
                Log.d("Geofence", "$name için geofence başarıyla eklendi!")
            }
            .addOnFailureListener {
                Log.e("Geofence", "$name için geofence eklenemedi!", it)
            }
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(requireContext(), GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("NOTIFICATION", "Bildirim izni verildi.")
        } else {
            Log.w("NOTIFICATION", "Kullanıcı bildirim iznini reddetti.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}