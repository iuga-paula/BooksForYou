package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.dao
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.database.BooksForYouDao
import com.android.example.booksforyou.databinding.FragmentSettingsBinding
import com.android.example.booksforyou.notifications
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            val currentSetting = dao.getSetting()
            readingReminder.isChecked = currentSetting.readingReminders
            buyingReminder.isChecked = currentSetting.buyingReminders
        }

        binding.handleReminders.setOnClickListener {
            if(binding.handleReminders.text == getString(R.string.enable_reminders)) {
                if (!readingReminder.isChecked) {
                    readingReminder.isChecked = true
                    notifications.subscribeTopic("Reading")
                    //update reminder from db
                    lifecycleScope.launch {
                        dao.updateReadingReminder(true)
                    }

                }
                if (!buyingReminder.isChecked) {
                    buyingReminder.isChecked = true
                    notifications.subscribeTopic("Buying")
                    //update reminder from db
                    lifecycleScope.launch {
                        dao.updateBuyingReminder(true)
                    }
                }
                binding.handleReminders.text = getString(R.string.disable_reminders)
            }
            else { //disable reminders
                if (readingReminder.isChecked) {
                    readingReminder.isChecked = false
                    notifications.unsubscribeTopic("Reading")
                    //update reminder from db
                    lifecycleScope.launch {
                        dao.updateReadingReminder(false)
                    }
                }
                if (buyingReminder.isChecked) {
                    buyingReminder.isChecked = false
                    notifications.unsubscribeTopic("Buying")
                    //update reminder from db
                    lifecycleScope.launch {
                        dao.updateBuyingReminder(false)
                    }
                }

                binding.handleReminders.text = getString(R.string.enable_reminders)

            }
        }

       readingReminder.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                notifications.unsubscribeTopic("Reading")
                binding.handleReminders.text = getString(R.string.enable_reminders)
                //update reminder from db
                lifecycleScope.launch {
                    dao.updateReadingReminder(false)
                }
            }
            else {
                notifications.subscribeTopic("Reading")
                binding.handleReminders.text = getString(R.string.disable_reminders)
                //update reminder from db
                lifecycleScope.launch {
                    dao.updateReadingReminder(true)
                }
            }
        }

        buyingReminder.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                notifications.unsubscribeTopic("Buying")
                binding.handleReminders.text = getString(R.string.enable_reminders)
                //update reminder from db
                lifecycleScope.launch {
                    dao.updateBuyingReminder(false)
                }
            }
            else {
                notifications.subscribeTopic("Buying")
                binding.handleReminders.text = getString(R.string.enable_reminders)
                //update reminder from db
                lifecycleScope.launch {
                    dao.updateBuyingReminder(true)
                }
            }
        }
        return binding.root
    }

}