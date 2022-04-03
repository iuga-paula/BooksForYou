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

//        val lastSettings = booksDao?.getSetting()
//        if (lastSettings != null) {
//            readingReminder.isChecked = lastSettings.readingReminders
//            buyingReminder.isChecked = lastSettings.buyingReminders
//        }

        binding.handleReminders.setOnClickListener {
            if(binding.handleReminders.text == getString(R.string.enable_reminders)) {
                if (!readingReminder.isChecked) {
                    readingReminder.isChecked = true
                    notifications.subscribeTopic("Reading")
//                    if (lastSettings != null) {
//                        lastSettings.readingReminders = true
//                        booksDao?.updateSetting(lastSettings)
//                    }
                }
                if (!buyingReminder.isChecked) {
                    buyingReminder.isChecked = true
                    notifications.subscribeTopic("Buying")
//                    if (lastSettings != null) {
//                        lastSettings.buyingReminders = true
//                        booksDao?.updateSetting(lastSettings)
//                    }
                }
                binding.handleReminders.text = getString(R.string.disable_reminders)
            }
            else { //diable reminders
                if (readingReminder.isChecked) {
                    readingReminder.isChecked = false
                    notifications.unsubscribeTopic("Reading")
//                    if (lastSettings != null) {
//                        lastSettings.readingReminders = false
//                        booksDao?.updateSetting(lastSettings)
//                    }
                }
                if (buyingReminder.isChecked) {
                    buyingReminder.isChecked = false
                    notifications.unsubscribeTopic("Buying")
//                    if (lastSettings != null) {
//                        lastSettings.buyingReminders = false
//                        booksDao?.updateSetting(lastSettings)
//                    }
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