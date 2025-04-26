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
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.NotificationItem
import com.softwarengineering.bloodconnect.databinding.FragmentNotificationBinding
import com.softwarengineering.bloodconnect.ui.adapter.NotificationAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar ayarları
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbarNotifications)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Notifications"

        val backArrow = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        backArrow?.setTint(ContextCompat.getColor(requireContext(), android.R.color.white))
        activity.supportActionBar?.setHomeAsUpIndicator(backArrow)
        setHasOptionsMenu(true)

        // RecyclerView setup
        val dummyData = listOf(
            NotificationItem("Medical Park Hospital", "A+", "15.03.2025 09:10", true),
            NotificationItem("Medicana Hospital", "0+", "02.01.2025 15:30", false),
            NotificationItem("Ataköy Hospital", "B+", "20.12.2024 13:05", false),
            NotificationItem("Medipol Hospital", "AB-", "08.11.2024 10:15", false),
            NotificationItem("Medical Park Hospital", "A-", "12.10.2024 20:26", true)
        )

        adapter = NotificationAdapter(dummyData)
        binding.recyclerNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNotifications.adapter = adapter
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
