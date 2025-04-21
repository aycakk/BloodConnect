package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homefragment = this


        with(binding){
            hospitalBtn.setOnClickListener {
                Log.d("TEST", "hospitalBtn tıklandı")
                findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
            }
            //Geri butonu
            buttonBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            imageButtondonation.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }

        return binding.root

    }




}