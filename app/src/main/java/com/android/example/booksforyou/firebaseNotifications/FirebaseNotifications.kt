package com.android.example.booksforyou.firebaseNotifications
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent

import android.content.Intent

import android.R.id
import android.app.Notification
import androidx.core.app.NotificationCompat
import android.R





class FirebaseNotifications:FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.i("FirebaseToken", "New token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val click_action = remoteMessage.notification!!.clickAction
        val id: String? = remoteMessage.data["link"]

        val intent = Intent(click_action)
        intent.putExtra("link", id)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val noti: Notification = Notification.Builder(this)
            .setContentTitle(remoteMessage.notification!!.title)
            .setContentText(remoteMessage.notification!!.body)
            .setSmallIcon(R.drawable.ic_notification_overlay)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager:NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        notificationManager.notify(0, noti)

    }

    fun createChannel(channelId: String, channelName: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Reading reminder"

            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    fun subscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "succesfully subscribed to $topic"
                if (!task.isSuccessful) {
                    msg = "failed to subscribe to $topic"
                }
                Log.i("Subscribed", msg)
            }
    }

    fun unsubscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "succesfully unsubscribed to $topic"
                if (!task.isSuccessful) {
                    msg = "failed to unsubscribe to $topic"
                }
                Log.i("Subscribed", msg)
            }
    }




}