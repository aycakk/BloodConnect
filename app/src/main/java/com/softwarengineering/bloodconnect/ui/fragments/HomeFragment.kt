package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHomeBinding
import com.softwarengineering.bloodconnect.ui.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private var currentPage = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homefragment = this

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding){
            hospitalBtn.setOnClickListener {
                Log.d("TEST", "hospitalBtn tıklandı")
                findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
            }
            //Geri butonu
            buttonBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            notification.setOnClickListener{
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_notificationsFragment)
            }
            profile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
            donationTracking.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_donationTrackingFragment)
            }
        }

        // Resim listesi
        val imageList = listOf(
            R.drawable.hhospital,
            R.drawable.blood_donation_benefits,
            R.drawable.world_blood_donor_day,
            //R.drawable.donate_blood
        )

        val adapter = ViewPagerAdapter(imageList)
        binding.viewPager.adapter = adapter

        // Dot Indicator'ı bağla
        binding.dotsIndicator.setViewPager2(binding.viewPager)

        // Otomatik kaydırma
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // 3 saniyede bir kay
            }
        }
        handler.post(runnable)
    }



    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable) // Sayfa değişince durdur
    }

    override fun onResume() {
        super.onResume()
        handler.post(runnable) // Sayfaya geri dönünce devam et
    }




}