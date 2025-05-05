package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentDonationFormBinding
import com.softwarengineering.bloodconnect.viewmodel.HospitalviewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationFormFragment : Fragment() {
  private lateinit var binding:FragmentDonationFormBinding
    private lateinit var viewmodel:HospitalviewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_donation_form, container, false)
        // Inflate the layout for this fragment
        with(binding){
        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

            save.setOnClickListener {

                try {
                    val donorname=donorname.text.toString()
                    val donoridnumber=donorid.text.toString()
                    val amounttext=units.text.toString().trim()
                    val amount=units.text.toString().toFloat()
                    val blood=spinnerblood.selectedItem.toString().trim()
                    if ( donoridnumber.isEmpty() || amounttext.isEmpty() || blood == "Select") {
                        Toast.makeText(requireContext(), "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    viewmodel.donationform(donoridnumber,donorname,amount,blood,)
                        Toast.makeText(requireContext(), "Accept form", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()

                }}

                catch (e:Exception){

                    Log.d("donationform", "$e")
                    }

            }


        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: HospitalviewModel by viewModels()
        viewmodel=tempviewmodel
    }


}