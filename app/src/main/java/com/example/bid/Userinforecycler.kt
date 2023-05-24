package com.example.bid

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bid.utill.POST_EXPIRE_MAX_TIME
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
private const val TAG = "Userinforecycler"
class Userinforecycler : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private  var userList=ArrayList<Bidderposts>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: UserAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v=inflater.inflate(R.layout.fragment_userinforecycler, container, false)
        recyclerView=v.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        userList= arrayListOf()
        adapter = UserAdapter(userList,requireContext())

        recyclerView.adapter = adapter
        databaseReference=FirebaseDatabase.getInstance().getReference("DisplayUserBids")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if(snapshot.exists()) {
                    snapshot.children.forEach { post ->
                        if (post.exists()) { //we can show this post because its not expired yet
                            val userID = post.key
                            var currentuser = FirebaseAuth.getInstance().currentUser!!.uid
                            Log.d(TAG, "live post id== : $userID ")
                            if (currentuser == userID) {
                                val fetchId = post.child("BiderBids")
                                fetchId.children.forEach { Bid ->
                                    val postids = Bid.key
                                    val fetchData =
                                        Bid.child("Data").getValue(Bidderposts::class.java)
                                    Log.d(TAG, " Data Fetch  : $fetchData ")
                                    if (fetchData != null) {
                                        userList.add(fetchData)
                                    }

                                }


                            }
                            recyclerView.adapter = adapter
                        }

                    }
                }

                        adapter.notifyDataSetChanged()

                }



            override fun onCancelled(error: DatabaseError) {

            }
        })

        return v}


}