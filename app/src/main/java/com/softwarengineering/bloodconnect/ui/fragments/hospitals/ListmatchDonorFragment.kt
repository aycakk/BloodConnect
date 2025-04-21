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
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListmatchDonorFragment : Fragment() {

    private lateinit var binding: FragmentListmatchDonorBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val recommend = Recommend()
    private var hospitalLocation: GeoPoint? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_listmatch_donor, container, false)
        // Inflate the layout for this fragment
        //binding.listmatch=this

        //!!!!!!!
        binding.spinnerbloodtype2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedBloodType = parent.getItemAtPosition(position).toString()

                // Now call your fetchDonorsAndDisplay with selected blood type
                fetchDonorsAndDisplay(selectedBloodType)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        //fetchDonorsAndDisplay()

        return binding.root
    }

    private fun fetchDonorsAndDisplay(recipientBloodType: String) {
        /*val dummyMatches = listOf(
            Match(donorID = "John Doe", matchScore = 92),
            Match(donorID = "Jane Smith", matchScore = 88),
            Match(donorID = "Ali Yilmaz", matchScore = 75),
            Match(donorID = "Maria Garcia", matchScore = 70),
            Match(donorID = "Chen Wei", matchScore = 65)
        )
        val adapter = MatchDonorAdapter(requireContext(), dummyMatches)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter*/
        //val recipientBloodType = "A+" // Replace with actual recipient's blood type
        //val hospitalLocation = floatArrayOf(0f, 0f) // Replace with actual lat/long of hospital
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("hospitals").document(uid).get()
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

                    Match(donorID = donor.donorID, matchScore = score.toInt(), donorName = donor.name, donorBloodType = donor.bloodType)
                }.sortedByDescending { it.matchScore }

                val adapter = MatchDonorAdapter(requireContext(), matches)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }



        }
    }
}
