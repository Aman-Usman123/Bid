package com.example.bid


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.sql.Timestamp


class sellActivity : AppCompatActivity() {
    private lateinit var uri: Uri
    private var storageReference = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)
        val imageview = findViewById<ImageView>(R.id.imageo)
        val product = findViewById<EditText>(R.id.editTextTextPersonName5)
        val catagory = findViewById<EditText>(R.id.editTextTextPersonName6)
        val sizes = findViewById<EditText>(R.id.editTextTextPersonName7)
        val weight = findViewById<EditText>(R.id.editTextTextPersonName8)
        val select2 = findViewById<Button>(R.id.uplod)
        val save = findViewById<Button>(R.id.save)
        storageReference = FirebaseStorage.getInstance()

        val imageGallery = registerForActivityResult(

            ActivityResultContracts.GetContent(),
            ActivityResultCallback { imagePath ->
                imageview.setImageURI(imagePath)
                uri = imagePath!!;
            })
        select2.setOnClickListener {
            imageGallery.launch("image/*")
        }
        save.setOnClickListener {

            storageReference.getReference("Images").child(System.currentTimeMillis().toString())
                .putFile(uri)

                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { downlaodUrl ->

                        val uid = FirebaseAuth.getInstance().currentUser!!.uid
                        val imageMap1 = mapOf(
                            "idofproduct" to uid,
                            "ImageUrl" to downlaodUrl.toString(),
                            "product" to product.text.toString(),
                            "catagory" to catagory.text.toString(),
                            "size" to sizes.text.toString(),
                            "weight" to weight.text.toString(),

                        )

                        val databaseReference = FirebaseDatabase.getInstance().getReference("UserPosts")
                        val timestamp=ServerValue.TIMESTAMP
                        databaseReference.child(uid).child("BidsRecord").setValue(null)

                        databaseReference.child(uid).child("UserInfo").setValue(imageMap1)
                        databaseReference.child(uid).child("timestamp").setValue(timestamp)

                            .addOnSuccessListener {

                                Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener {

                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }


        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")



    override fun onBackPressed() {
        // Start the new activity
        val intent = Intent(this, DatabaseRecycler::class.java)
        startActivity(intent)

        // Call super.onBackPressed() to exit the current activity
        super.onBackPressed()
    }

}







