package com.example.bid

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



private const val TAG = "PushNotification"


class PushNotification {
    private var pushNotificationAPI: PushNotificationAPI = RetrofitPushNotification().getAPi()


    private fun sendPushNotification(notification: PushNotificationData) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = pushNotificationAPI.postNotification(notification)
                if (response.isSuccessful) {
//                    Log.d(TAG, "sendPushNotification: Response = ${Gson().toJson(response)}")
                } else {
                    Log.d(TAG, "sendPushNotification: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.d(TAG, "sendPushNotification: ${e.message}")
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun createAndSendNotification(receiverUid: String,receiverToken :String) {
        CoroutineScope(Dispatchers.Default).launch {
            val databaseReference = FirebaseDatabase.getInstance().getReference("Bids")
            databaseReference.child(receiverUid).get().addOnCompleteListener {
                Log.d(TAG, "getHighestBid: ${it.isSuccessful} && ${it.result.exists()}")
                if (it.isSuccessful && it.result.exists()) {

                    it.result.children.forEach { data ->
                        val bidderId = data.key!!

                        val productName = data.child("ProductName").value as String
                        Log.d(TAG, "getHighestBid: bidAmound =  ${productName}")

            val recipientToken =receiverToken


            val notification = NotificationData("Online Auction System",
                "Congratulationss!You are Bid winner",
                productName,
                receiverUid
                )
            if (recipientToken != null) {
                val pushNotification = PushNotificationData(notification, recipientToken)
                sendPushNotification(pushNotification)
            } else {
                Log.d(TAG, "createAndSendNotification: Recipient Token Not Found")
            }

        }
    }}
}}}


