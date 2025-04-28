package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalLoginBinding
import com.softwarengineering.bloodconnect.utils.SessionManager
import com.softwarengineering.bloodconnect.viewmodel.HospitalregisterViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalLoginFragment : Fragment() {
    private lateinit var binding:FragmentHospitalLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_login, container, false)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)


        binding.buttonLogin.setOnClickListener {
            viewModel.loginhospital(
                binding.edittextemail.text.toString(),
                binding.editTextpassword.text.toString(),
                onSuccess = {
                    fetchHospitalName() // Önce hospitalName al
                    Navigation.findNavController(it).navigate(R.id.action_hospitalLoginFragment_to_hospitalHomeFragment)
                },
                onFailure = {
                    Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                },
                onUnapproved = {
                    Toast.makeText(requireContext(), "Your hospital account has not been approved yet.", Toast.LENGTH_LONG).show()
                }
            )
        }


        //Geri butonu
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: LoginViewModel by viewModels()
        viewModel=tempviewmodel
    }

    private fun fetchHospitalName() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        db.collection("hospitals")
            .document(uid ?: "")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val hospitalName = document.getString("hospitalName")
                    SessionManager.currentHospitalName = hospitalName
                }
            }
    }


}