package com.example.bid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Descriptionpage : AppCompatActivity() {

    private lateinit var firebase:FirebaseDatabase
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptionpage)
        val image= findViewById<ImageView>(R.id.imageview1)
        val productImage = intent.getStringExtra("productImage")

        Picasso.get().load(productImage).into(image)
        val productDescription = intent.getStringExtra("productDescription")
        val productName = intent.getStringExtra("productName")
        findViewById<TextView>(R.id.pname).text = productName
        findViewById<TextView>(R.id.pdes).text = productDescription

       val btn=findViewById<Button>(R.id.buttonbid)
        btn.setOnClickListener {

saveBBIDRecord()
    }

}
    fun saveBBIDRecord() {
        val amount=findViewById<EditText>(R.id.Amount)
        val id=intent.getStringExtra("idofproduct")
        // Generate a unique key for the new record
        if(id!=null){
            var databaseRefrence=FirebaseDatabase.getInstance().getReference("Bids")
        val uids=FirebaseAuth.getInstance().currentUser!!.uid
            val biddata = mapOf(
                "Amount" to amount.text.toString(),
                )
     //   databaseRefrence.child(id).child("BidsRecord").push(uids)
        val newRecordRef = databaseRefrence.child(id).child("BidsRecord").child(uids)


        newRecordRef.setValue(biddata)
    }}}