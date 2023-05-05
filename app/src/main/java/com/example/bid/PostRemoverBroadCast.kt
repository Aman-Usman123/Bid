package com.example.bid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bid.utill.*
import com.google.firebase.database.FirebaseDatabase


private const val TAG ="PostRemoverBroadCast"
class PostRemoverBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive")

        if (intent!=null){
            val postId= intent.getStringExtra(POST_ID_EXPIRED)?:null
            if (postId!=null){
                removeThisPostFromFireBase(postId)
            }
        }
    }

    private fun removeThisPostFromFireBase(postId: String) {
      val databaseReference= FirebaseDatabase.getInstance().getReference("UserImagesData")
        databaseReference.child(postId).removeValue().addOnCompleteListener { 
            if (it.isSuccessful) {
                Log.d(TAG, "removeThisPostFromFireBase: post removed : $postId ")
            }else{
                Log.d(TAG, "removeThisPostFromFireBase: Error ${it.exception}")
            }
        }
    }
}