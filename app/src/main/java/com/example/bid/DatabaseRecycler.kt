package com.example.bid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.lang.ref.Reference

class DatabaseRecycler : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesList:ArrayList<usserImages>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_recycler)
        recyclerView=findViewById(R.id.recyclerImage)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


        imagesList= arrayListOf()
        databaseReference=FirebaseDatabase.getInstance().getReference("UserImagesData")
        recyclerView.adapter=imageAdapter(imagesList,this@DatabaseRecycler)



        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {
                    for(dataSnapshot in snapshot.children){
                        val image=dataSnapshot.getValue(usserImages::class.java)
                        imagesList.add(image!!)
                    }
                    recyclerView.adapter=imageAdapter(imagesList,this@DatabaseRecycler)
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@DatabaseRecycler,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })



        val bottomnavigationview:BottomNavigationView=findViewById(R.id.bottomNavigationView)

        bottomnavigationview.setOnItemSelectedListener{

            when(it.itemId)
            {
                R.id.Sell->{ startActivity(Intent(this, sellActivity::class.java))}


            }
            true

        }
     }}