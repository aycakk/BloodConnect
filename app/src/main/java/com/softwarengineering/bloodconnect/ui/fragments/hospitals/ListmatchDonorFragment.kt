package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentListmatchDonorBinding
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Donation
import com.softwarengineering.bloodconnect.data.model.Match
import com.softwarengineering.bloodconnect.ui.adapter.MatchDonorAdapter
import com.softwarengineering.bloodconnect.Recommend
import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.AdapterView
import com.google.firebase.auth.FirebaseAuth
import com.softwarengineering.bloodconnect.data.model.Hospital
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.firestore.SetOptions

@AndroidEntryPoint
class ListmatchDonorFragment : Fragment() {

    private lateinit var binding: FragmentListmatchDonorBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val recommend = Recommend()
    private var hospitalLocation: GeoPoint? = null
    private var hospID: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_listmatch_donor, container, false)

        binding.spinnerbloodtype2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedBloodType = parent.getItemAtPosition(position).toString()

                fetchDonorsAndDisplay(selectedBloodType)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        return binding.root
    }

    private fun fetchDonorsAndDisplay(recipientBloodType: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            hospID = uid
            FirebaseFirestore.getInstance().collection("hospital").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val hospital = document.toObject(Hospital::class.java)
                        hospital?.location?.let { geoPoint ->
                            hospitalLocation = geoPoint
                        } ?: Log.d("HospitalLocation", "Location data is null.")
                    } else {
                        Log.d("HospitalLocation", "No hospital found for this UID.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("HospitalLocation", "Error retrieving hospital data", exception)
                }
        } else {
            Log.e("HospitalLocation", "User is not authenticated.")
        }

        firestore.collection("donor").get().addOnSuccessListener { donorSnap ->
            val donors = donorSnap.documents.mapNotNull { it.toObject(Donor::class.java) }

            firestore.collection("donation").get().addOnSuccessListener { donationSnap ->
                val allDonations =
                    donationSnap.documents.mapNotNull { it.toObject(Donation::class.java) }

                val matches = donors.map { donor ->
                    val donorDonations = allDonations.filter { it.donorID == donor.donorID }
                    val score = recommend.scoreDonor(
                        requireContext(),
                        donor,
                        recipientBloodType,
                        hospitalLocation,
                        donorDonations
                    )

                    Match(donorID = donor.donorID, matchScore = score.toInt(), donorName = donor.name, donorBloodType = donor.bloodType, hospitalID = hospID, neededBloodType = recipientBloodType )
                }.filter{it.matchScore > 0}.sortedByDescending { it.matchScore }

                for (match in matches) {
                    val documentId = "${match.hospitalID}_${match.donorID}_${match.neededBloodType}"

                    firestore.collection("match")
                        .document(documentId)
                        .set(match, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("UploadMatch", "Match for ${match.donorID} uploaded/updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e("UploadMatch", "Error uploading match for ${match.donorID}", e)
                        }
                }

                val adapter = MatchDonorAdapter(requireContext(), matches)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }


            binding.buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
