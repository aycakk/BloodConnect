package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.softwarengineering.bloodconnect.databinding.FragmentLoginBinding
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false)

        // Inflate the layout for this fragment
        binding.buttonLogin.setOnClickListener {
            loginViewModel.loginUser(
                binding.edittextemail.text.toString(),
                binding.editTextpassword.text.toString(),
                onSuccess = {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid

                    if (uid != null) {
                        // 🔎 Firestore'dan donor adını çek
                        FirebaseFirestore.getInstance().collection("donor")
                            .document(uid)
                            .get()
                            .addOnSuccessListener { document ->
                                val donorName = document.getString("name") ?: "Unknown"
                                Log.d("Login", "Donor name: $donorName")

                                // SharedPreferences'e yaz
                                val sharedPref = requireActivity().getSharedPreferences("user_info", 0)
                                sharedPref.edit().putString("donor_name", donorName).apply()

                                // HomeFragment'a yönlendir
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Failed to fetch donor name.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(requireContext(), "UID is null", Toast.LENGTH_SHORT).show()
                    }
                },
                onFailure = { Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()})

        }
        //Geri butonu
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel:LoginViewModel by viewModels()
        loginViewModel=tempviewmodel
    }




}