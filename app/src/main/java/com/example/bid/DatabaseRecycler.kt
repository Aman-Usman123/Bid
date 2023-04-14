package com.example.bid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class DatabaseRecycler : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var userList:ArrayList<usserImages>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_database_recycler)
        recyclerView=findViewById(R.id.recyclerImage)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)


        userList= arrayListOf()
        databaseReference=FirebaseDatabase.getInstance().getReference("UserImagesData")

         databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                   userList.clear()
                if(snapshot.exists())
                {
                    for(dataSnapshot in snapshot.children)
                    {
                        val user1=dataSnapshot.child("UserInfo").getValue(usserImages::class.java)

                        if (user1 !=null )
                        {

                            userList.add(user1)

                        }

                    }

                }
                val adapter = imageAdapter(userList,this@DatabaseRecycler)
                adapter.setOnItemClickListener(object : imageAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        // Handle click event here

                        val intent =Intent(this@DatabaseRecycler,Descriptionpage::class.java)
                        intent.putExtra("productImage", userList[position].ImageUrl)
                        intent.putExtra("productName", userList[position].product)
                        intent.putExtra("productDescription", userList[position].catagory)
                        intent.putExtra("productweight", userList[position].weight)
                        startActivity(intent)
                    }
                })

                recyclerView.adapter=adapter


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


    }



}



