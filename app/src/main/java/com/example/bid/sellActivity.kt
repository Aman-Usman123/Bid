package com.example.bid

import android.annotation.SuppressLint
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class sellActivity : AppCompatActivity() {
    private lateinit var imageview: ImageView
    private lateinit var select: Button
    private lateinit var select2: Button

    private lateinit var uri: Uri
    private var storageReference= Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)
val imageview=findViewById<ImageView>(R.id.imageo)
        val product=findViewById<EditText>(R.id.editTextTextPersonName5)
        val catagory=findViewById<EditText>(R.id.editTextTextPersonName6)
        val sizes=findViewById<EditText>(R.id.editTextTextPersonName7)
        val weight=findViewById<EditText>(R.id.editTextTextPersonName8)
        val select2=findViewById<Button>(R.id.uplod)
        val save=findViewById<Button>(R.id.save)
        storageReference= FirebaseStorage.getInstance()

        val imageGallery=registerForActivityResult(

            ActivityResultContracts.GetContent(),
            ActivityResultCallback{ imagePath ->
                imageview.setImageURI(imagePath)
                uri= imagePath!!;
            })
        select2.setOnClickListener {
            imageGallery.launch("image/*")
        }
        save.setOnClickListener{

            storageReference.getReference("Images").child(System.currentTimeMillis().toString())
                .putFile(uri)

                .addOnSuccessListener {task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                            downlaodUrl ->

                    val uid=FirebaseAuth.getInstance().currentUser!!.uid
                    val imageMap = mapOf(
                        "ImageUrl" to downlaodUrl.toString())
                        val userdata=usserImages().apply {
                            this.product= product.text.toString()
                            this.catagory=catagory.text.toString()
                            this.sizes=sizes.text.toString()
                            this.weight= weight.text.toString()

                        }
                        val databaseReference=FirebaseDatabase.getInstance().getReference("UserImagesData")
                        databaseReference.child(uid).child("userdata").setValue(userdata)
                        databaseReference.child(uid).child("Url").setValue(imageMap)

                        .addOnSuccessListener {

                            Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener{

                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                }
        }


            }

            }
           }






