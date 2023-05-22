package com.example.bid

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bid.utill.*
import com.google.firebase.database.FirebaseDatabase


private const val TAG = "PostRemoverBroadCast"

class PostRemoverBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive $intent")

        if (intent != null) {
            val postId = intent.getStringExtra(POST_ID_EXPIRED) ?: null
            if (postId != null) {
                getHighestBid(postId)

                Log.d(TAG, "getHighestBid: ${postId}")
            }
        }
    }


    private fun getHighestBid(postId: String) {
        Log.d(TAG, "getHighestBid: ${postId}")
        val listOfBids = ArrayList<Pair<String, Double>>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("Bids")
        databaseReference.child(postId).child("BidsRecord").get().addOnCompleteListener {
            Log.d(TAG, "getHighestBid: ${it.isSuccessful} && ${it.result.exists()}")
            if (it.isSuccessful && it.result.exists()) {

                it.result.children.forEach { data ->
                    val bidderId = data.key!!
                    val bidAmount = data.child("Amount").value as String
                    Log.d(TAG, "getHighestBid: bidAmound =  ${bidAmount}")
                    val bid = Pair(bidderId, bidAmount.toDouble())
                    listOfBids.add(bid)

                }
                var highestBidder = Pair("", 0.0)
                listOfBids.forEach { bid ->
                    if (bid.second > highestBidder.second) {
                        highestBidder = bid
                    }
                }
                sendNotificationToWinner(highestBidder, postId)
                removeThisPostFromFireBase(postId)
            } else {
                Log.d(TAG, "getHighestBid: no result found")
            }
        }

    }

    private fun sendNotificationToWinner(highestBidder: Pair<String, Double>, postId: String) {
        Log.d(
            TAG,
            "sendNotificationToWinner: ${highestBidder.first} won bid on ${postId} by bidding amount = ${highestBidder.second}"
        )
    }

    @SuppressLint("SuspiciousIndentation")
    private fun removeThisPostFromFireBase(postId: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("UsersPosts")
        databaseReference.child("").removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "removeThisPostFromFireBase: post removed : $postId ")
            } else {
                Log.d(TAG, "removeThisPostFromFireBase: Error ${it.exception}")
            }
        }
    }
}