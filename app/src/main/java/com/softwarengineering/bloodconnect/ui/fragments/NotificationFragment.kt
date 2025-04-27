package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.NotificationItem
import com.softwarengineering.bloodconnect.databinding.FragmentNotificationBinding
import com.softwarengineering.bloodconnect.ui.adapter.NotificationAdapter

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotificationAdapter
    private val notificationsList = mutableListOf<NotificationItem>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        setupToolbar()
        setupRecyclerView()
        fetchNotificationsFromFirestore()
    }

    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbarNotifications)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Notifications"

        val backArrow = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        backArrow?.setTint(ContextCompat.getColor(requireContext(), android.R.color.white))
        activity.supportActionBar?.setHomeAsUpIndicator(backArrow)
        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter(notificationsList)
        binding.recyclerNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNotifications.adapter = adapter
    }

    private fun fetchNotificationsFromFirestore() {
        db.collection("notification")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                notificationsList.clear()
                if (snapshots != null) {
                    for (doc in snapshots) {
                        val bloodType = doc.getString("bloodType") ?: ""
                        val hospitalName = doc.getString("hospitalName") ?: ""
                        val timestamp: Long = when (val value = doc.get("timestamp")) {
                            is Number -> value.toLong()
                            else -> 0L
                        }

                        val notificationItem = NotificationItem(
                            bloodType = bloodType,
                            hospitalName = hospitalName,
                            timestamp = timestamp
                        )
                        notificationsList.add(notificationItem)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
