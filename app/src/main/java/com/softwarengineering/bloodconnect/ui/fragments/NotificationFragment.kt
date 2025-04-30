package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
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
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("notification")
            .whereEqualTo("donorID", currentUserId) //SADECE o kullanıcıya ait olanlar
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshots ->
                notificationsList.clear()

                for (doc in snapshots) {
                    val message = doc.getString("message") ?: continue

                    val timestamp: Long = when (val raw = doc.get("timestamp")) {
                        is com.google.firebase.Timestamp -> raw.toDate().time
                        is Number -> raw.toLong()
                        else -> 0L
                    }


                    notificationsList.add(
                        NotificationItem(
                            message = message,
                            timestamp = timestamp
                        )
                    )
                }

                adapter.notifyDataSetChanged()
                // Bildirim yoksa kullanıcıya bilgi ver
                if (notificationsList.isEmpty()) {
                    Toast.makeText(requireContext(), "No notifications yet.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch notifications: ${it.message}", Toast.LENGTH_SHORT).show()
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
