package com.example.bid


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bid.utill.POST_EXPIRE_MAX_TIME
import com.example.bid.utill.POST_ID_EXPIRED
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class DatabaseRecycler : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  var userList=ArrayList<usserImages>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: imageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_database_recycler)
        recyclerView=findViewById(R.id.recyclerImage)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        userList= arrayListOf()
        adapter = imageAdapter(userList,this)
        adapter.setOnItemClickListener(object : imageAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                // Handle click event here

                                val intent =
                                    Intent(this@DatabaseRecycler, Descriptionpage::class.java)
                                intent.putExtra("idofproduct", userList[position].idofproduct)
                                intent.putExtra("productImage", userList[position].ImageUrl)
                                intent.putExtra("productName", userList[position].product)
                                intent.putExtra("productDescription", userList[position].catagory)
                                intent.putExtra("productweight", userList[position].weight)
                                startActivity(intent)
                            }
                        })


                      recyclerView.adapter = adapter
        databaseReference=FirebaseDatabase.getInstance().getReference("UserPosts")
databaseReference.addValueEventListener(object : ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        userList.clear()
            if(snapshot.exists())
            {
                snapshot.children.forEach{post ->
                    if(post.exists())
                    {
                        Log.d("ok", "onDataChange: $post ")
                        val anyTime:Any? =post.child("timestamp").getValue()
                        var time =0L
                        if(anyTime != null){
                            time = anyTime as Long
                        }
                        val currentTime=System.currentTimeMillis()
                        val spentTime=currentTime-time
                        Log.d("ok", "onDataChange: ${post.key} \n  c=$currentTime \n s = $spentTime ")
                        if(spentTime>=POST_EXPIRE_MAX_TIME)
                        {
                            val userID= post.key
                            Log.d("ok", "expired post id : $userID ")
                        }else
                        {
                            //we can show this post because its not expired yet
                            val userID= post.key
                            Log.d("ok", "live post id : $userID ")
                            val userInfo= post.child("UserInfo").getValue(usserImages::class.java)
                            if (userInfo != null) {
                                Log.d("ok", " post  : $userInfo ")
                                userList.add(userInfo)
                                val remainingTime = POST_EXPIRE_MAX_TIME- spentTime
                                setAlarmForThisPost(remainingTime,userID)
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
//         databaseReference.addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//               userList.clear()
//                if (snapshot.exists()) {
//                    for (dataSnapshot in snapshot.children) {
//
//                        val user1 = dataSnapshot.child("UserInfo").getValue(usserImages::class.java)
//
//
//                        val timestampRef = databaseReference.child(uid).child("timestamp")
//                        timestampRef.addValueEventListener(object : ValueEventListener {
//                            override fun onDataChange(datasSnapshot: DataSnapshot) {
//                                // Get the timestamp value from the dataSnapshot
//                               val timestamp = datasSnapshot.getValue(Long::class.java)
//
//                                // Get the current time in milliseconds
//                              //  val currentTime = System.currentTimeMillis()
//                                // Compare the timestamp value with the current time
//                                if (user1 != null ) {
//                                    // The timestamp is less than 1 minute old, so add the product
//                                    userList.add(user1)
//
//                                }
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                                // Handle errors here
//                            }
//                        })
//
//
//                        val adapter = imageAdapter(userList, this@DatabaseRecycler)
//                        recyclerView.adapter = adapter
//                        adapter.setOnItemClickListener(object : imageAdapter.OnItemClickListener {
//                            override fun onItemClick(position: Int) {
//                                // Handle click event here
//
//                                val intent =
//                                    Intent(this@DatabaseRecycler, Descriptionpage::class.java)
//                                intent.putExtra("productImage", userList[position].ImageUrl)
//                                intent.putExtra("productName", userList[position].product)
//                                intent.putExtra("productDescription", userList[position].catagory)
//                                intent.putExtra("productweight", userList[position].weight)
//                                startActivity(intent)
//                            }
//                        })
//
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//                Toast.makeText(this@DatabaseRecycler,error.toString(),Toast.LENGTH_SHORT).show()
//            }
//
//         })

        val bottomnavigationview:BottomNavigationView=findViewById(R.id.bottomNavigationView)

        bottomnavigationview.setOnItemSelectedListener{

            when(it.itemId)
            {
                R.id.Sell->{ startActivity(Intent(this, sellActivity::class.java))}
                R.id.home->{ startActivity(Intent(this, DatabaseRecycler::class.java))}
                R.id.setting->{ startActivity(Intent(this, Settings::class.java))}
            }
            true

        }

    }

    private fun setAlarmForThisPost(difference: Long, userID: String?) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, PostRemoverBroadCast::class.java).apply {
            putExtra(POST_ID_EXPIRED,userID)
        }
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }else{
                PendingIntent.getBroadcast(this, 0, intent, 0)
            }
        val timeInMilliseconds = System.currentTimeMillis() + difference // difference seconds from now

        alarmManager[AlarmManager.RTC_WAKEUP, timeInMilliseconds] = pendingIntent

    }

    override fun onBackPressed() {
        finishAffinity()

    }



}



