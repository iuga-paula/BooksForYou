package com.android.example.booksforyou

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.example.booksforyou.books.AllBooks
import com.android.example.booksforyou.databinding.ActivityMainBinding
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.multidex.MultiDex
import com.android.example.booksforyou.firebaseNotifications.FirebaseNotifications
import com.android.example.booksforyou.navigation.Wishes
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


var books: AllBooks = AllBooks()
var wishlist: Wishes = Wishes()
var notifications: FirebaseNotifications = FirebaseNotifications()
var callbackManager = CallbackManager.Factory.create();
var userId: String? = null
var userName: String? = null

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Main", "reintialised books")
        MultiDex.install(this)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(application);

        val navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navView, navController)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i("FirebaseToken", "New token ${task.result}")
            }
        })

        //create FCM Channel
        notifications.createChannel(
            "fcm_default_channel",
            "Reading reminder",
        this)

        notifications.subscribeTopic("Reading")
        notifications.subscribeTopic("Buying")


        val intent = intent
        val extras = intent.extras
        extras?.let {
            val link = it.getString("link")
            link?.let{
                navController.navigate(R.id.action_helloFragment_to_yourBooks)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        finish()
    }

}