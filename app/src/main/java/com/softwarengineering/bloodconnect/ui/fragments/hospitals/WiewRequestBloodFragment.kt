package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R


import com.softwarengineering.bloodconnect.databinding.FragmentWiewRequestBloodBinding
import com.softwarengineering.bloodconnect.ui.adapter.ViewRequestAdapter
import com.softwarengineering.bloodconnect.viewmodel.HospitalviewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WiewRequestBloodFragment : Fragment() {
    private lateinit var binding:FragmentWiewRequestBloodBinding
    private lateinit var viewmodel:HospitalviewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,
            R.layout.fragment_wiew_request_blood, container, false)

        binding.lifecycleOwner =viewLifecycleOwner

        viewmodel.requestlist.observe(viewLifecycleOwner){
            val requestadapter=ViewRequestAdapter(requireContext(),it,viewmodel)
            binding.requestadapter=requestadapter

        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        onResume()


        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: HospitalviewModel by viewModels()
      viewmodel=tempviewmodel
    }


}