package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.booksforyou.R
import com.android.example.booksforyou.databinding.FragmentSettingsBinding
import com.android.example.booksforyou.databinding.FragmentWishlistBinding
import com.android.example.booksforyou.databinding.FragmentYourBooksBinding
import com.android.example.booksforyou.notifications

class Settings : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater,
            R.layout.fragment_settings, container, false
        )

        val readingReminder = binding.readingReminderSwitch
        val buyingReminder = binding.buyingReminderSwitch

        binding.handleReminders.setOnClickListener {
            if(binding.handleReminders.text == getString(R.string.enable_reminders)) {
                if (!readingReminder.isChecked) {
                    readingReminder.isChecked = true
                    notifications.subscribeTopic("Reading")
                }
                if (!buyingReminder.isChecked) {
                    buyingReminder.isChecked = true
                    notifications.subscribeTopic("Buying")
                }
                binding.handleReminders.text = getString(R.string.disable_reminders)
            }
            else { //diable reminders
                if (readingReminder.isChecked) {
                    readingReminder.isChecked = false
                    notifications.unsubscribeTopic("Reading")
                }
                if (buyingReminder.isChecked) {
                    buyingReminder.isChecked = false
                    notifications.unsubscribeTopic("Buying")
                }

                binding.handleReminders.text = getString(R.string.enable_reminders)

            }
        }

       readingReminder.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                notifications.unsubscribeTopic("Reading")
                binding.handleReminders.text = getString(R.string.enable_reminders)
            }
            else {
                notifications.subscribeTopic("Reading")
                binding.handleReminders.text = getString(R.string.disable_reminders)
            }
        }

        buyingReminder.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                notifications.unsubscribeTopic("Buying")
                binding.handleReminders.text = getString(R.string.enable_reminders)
            }
            else {
                notifications.subscribeTopic("Buying")
                binding.handleReminders.text = getString(R.string.enable_reminders)
            }
        }
        return binding.root
    }

}