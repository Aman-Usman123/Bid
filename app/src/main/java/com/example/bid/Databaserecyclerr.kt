package com.example.bid

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bid.utill.POST_EXPIRE_MAX_TIME
import com.example.bid.utill.POST_ID_EXPIRED
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Databaserecyclerr : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var userList = ArrayList<usserImages>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: imageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_databaserecyclerr, container, false)
        recyclerView = v.findViewById(R.id.recyclerImage)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        userList = arrayListOf()
        adapter = imageAdapter(userList, requireContext())
        val uid=FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("Registration_Record").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("MissingInflatedId")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(usserImages::class.java)

                    val name=v.findViewById<TextView>(R.id.name)
                    val balance=v.findViewById<TextView>(R.id.balance)
                     name.text = user?.username
                   balance.text = user?.Balance
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
        adapter.setOnItemClickListener(object : imageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle click event here

                val intent =
                    Intent(requireContext(), Descriptionpage::class.java)
                intent.putExtra("idofproduct", userList[position].idofproduct)
                intent.putExtra("productImage", userList[position].ImageUrl)
                intent.putExtra("productName", userList[position].product)
                intent.putExtra("productDescription", userList[position].catagory)
                intent.putExtra("productweight", userList[position].weight)
                startActivity(intent)
            }
        })


        recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("UserPosts")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    snapshot.children.forEach { post ->
                        if (post.exists()) {
                            Log.d("ok", "onDataChange: $post ")
                            val anyTime: Any? = post.child("timestamp").getValue()
                            var time = 0L
                            if (anyTime != null) {
                                time = anyTime as Long
                            }
                            val currentTime = System.currentTimeMillis()
                            val spentTime = currentTime - time
                            Log.d(
                                "ok",
                                "onDataChange: ${post.key} \n  c=$currentTime \n s = $spentTime "
                            )
                            if (spentTime >= POST_EXPIRE_MAX_TIME) {
                                val userID = post.key
                                Log.d("ok", "expired post id : $userID ")
                            } else {
                                //we can show this post because its not expired yet
                                val userID = post.key
                                Log.d("ok", "live post id : $userID ")
                                val userInfo =
                                    post.child("UserInfo").getValue(usserImages::class.java)
                                if (userInfo != null) {
                                    Log.d("ok", " post  : $userInfo ")
                                    userList.add(userInfo)
                                    val remainingTime = POST_EXPIRE_MAX_TIME - spentTime
                                   setAlarmForThisPost(remainingTime, userID)
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Unit
            }

        })





        return v
    }

    private fun setAlarmForThisPost(difference: Long, userID: String?) {
        if (isAdded) {


            val alarmManager =
                requireActivity().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
            val intent = Intent(requireContext(), PostRemoverBroadCast::class.java).apply {
                putExtra(POST_ID_EXPIRED, userID)
            }
            val pendingIntent =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getBroadcast(
                        requireContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                } else {
                    PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
                }
            val timeInMilliseconds =
                System.currentTimeMillis() + difference // difference seconds from now

            alarmManager[AlarmManager.RTC_WAKEUP, timeInMilliseconds] = pendingIntent

        }


    }





    }


