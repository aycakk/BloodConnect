package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalHomeBinding
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalHomeFragment : Fragment() {
private lateinit var binding: FragmentHospitalHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_home, container, false)
        // Inflate the layout for this fragment


        with(binding){
            hospitalBtn2.setOnClickListener {
                Log.d("TEST", "hospitalBtn tıklandı")
                val bundle = Bundle().apply {
                    putBoolean("showOnlyBloodCenters", true)
                }
                findNavController().navigate(R.id.action_hospitalHomeFragment_to_mapFragment, bundle)
            }
            imageButtonListdonor.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_hospitalHomeFragment_to_listmatchDonorFragment)
            }
            vreatebloodrequestcd.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_hospitalHomeFragment_to_createBloodRequestFragment)
            }
        }
        return binding.root
    }


}