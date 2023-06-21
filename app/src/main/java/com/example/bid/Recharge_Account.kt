package com.example.bid

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class Recharge_Account : AppCompatActivity() {
    private lateinit var uri: Uri
    @SuppressLint("MissingInflatedId")
    private var storageReference = Firebase.storage
    private lateinit var progressBar:ProgressBar
    private var isProgressBar=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageReference = FirebaseStorage.getInstance()
        setContentView(R.layout.activity_recharge_account)
        val imageview = findViewById<ImageView>(R.id.imaget)
        val uplod =findViewById<Button>(R.id.uplod)
        val rechargeamount=findViewById<EditText>(R.id.RechargeAmount)
        progressBar=findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility=if(isProgressBar)View.VISIBLE else View.GONE
        val imageGallery = registerForActivityResult(

            ActivityResultContracts.GetContent(),
            ActivityResultCallback { imagePath ->
                imageview.setImageURI(imagePath)
                uri = imagePath!!;
            })
        uplod.setOnClickListener {
            imageGallery.launch("image/*")



        }

        val confirm =findViewById<Button>(R.id.confirm)
        confirm.setOnClickListener {
            var amounts=rechargeamount.text.toString()
            if (amounts.isNotEmpty() && imageview.drawable!==null) {
                isProgressBar=true
                progressBar.visibility=View.VISIBLE

                storageReference.getReference("RechageAmountRecord")
                    .child(System.currentTimeMillis().toString())
                    .putFile(uri)

                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { downlaodUrl ->

                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
                            val imageMap1 = mapOf(

                                "ImageUrl" to downlaodUrl.toString(),

                                )

                            val databaseReference = FirebaseDatabase.getInstance().getReference()

                            databaseReference.child("Registration_Record").child(uid)
                                .child("picture").setValue(imageMap1).addOnSuccessListener {
                                   rechargeamount.text.clear()
                                    imageview.setImageDrawable(null)
                                    isProgressBar=false
                                    progressBar.visibility=View.GONE

                                    Toast.makeText(this, "Your Recharge amount will be varified After 30 minutes", Toast.LENGTH_SHORT).show()


                                }


                        }

                    }.addOnFailureListener {

                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this,"Your image or recharge amount is missing",Toast.LENGTH_SHORT).show()

            }
        }
}override fun onBackPressed() {
    val intent = Intent(this, playing::class.java)
    startActivity(intent)
    super.onBackPressed()

}}