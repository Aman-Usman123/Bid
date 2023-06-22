package com.example.bid

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class PushNotificationReceiveService : FirebaseMessagingService() {


    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        Log.d("TapBack", "onMessageReceived: ${message.data["ledgerUID"]}")

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) getActivity(this,
            0,
            intent,
            FLAG_MUTABLE)
        else getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification =
            NotificationCompat.Builder(this, getString(R.string.push_notification_channel_id))
                .apply {
                    setContentTitle(message.data["title"])
                    setStyle(NotificationCompat.BigTextStyle().bigText(message.data["message"]+"\n ${message.data["ProductName"]}"
                            ))
                    setSmallIcon(R.mipmap.ic_launcher)
                    setAutoCancel(true)
                    setContentIntent(pendingIntent)

                }.build()
        notificationManager.notify(notificationID, notification)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "LedgerO Entries"
        val channel = NotificationChannel(getString(R.string.push_notification_channel_id),
            channelName, IMPORTANCE_HIGH).apply {
            description = "Display all entries update notifications"
            enableLights(true)
            lightColor = Color.BLUE
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
      //  FirebaseTokenManager.updateUserFirebaseToken(User.userID!!, token)
    }
}