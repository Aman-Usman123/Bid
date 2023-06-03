package com.example.bid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Descriptionpage : AppCompatActivity() {

    private lateinit var numberField: EditText
private  var productImage:String?=null
    private  var productName:String?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptionpage)
        val image= findViewById<ImageView>(R.id.imageview1)

         productImage = intent.getStringExtra("productImage")
        productName = intent.getStringExtra("productName")
        Picasso.get().load(productImage).into(image)
        val productDescription = intent.getStringExtra("productDescription")

        findViewById<TextView>(R.id.pname).text = productName
        findViewById<TextView>(R.id.pdes).text = productDescription
val Recharge=findViewById<Button>(R.id.Recharge)
        Recharge.setOnClickListener {
           val intent= Intent(this,Recharge_Account::class.java)
            startActivity(intent)
        }
       val btn=findViewById<Button>(R.id.buttonbid)
        btn.setOnClickListener {

saveBBIDRecord()
    }

}
    fun saveBBIDRecord() {

        val postid=intent.getStringExtra("idofproduct")
        // Generate a unique key for the new record
        if(postid!=null) {
            numberField = findViewById(R.id.amount)
            var number = numberField.text.toString()
            if (number.isEmpty()) {
                Toast.makeText(
                    getApplicationContext(),
                    "Please Enter Bid Amount",
                    Toast.LENGTH_SHORT
                ).show();

            } else {
                var uids = FirebaseAuth.getInstance().currentUser!!.uid
                var databaseReference =
                    FirebaseDatabase.getInstance().getReference("Registration_Record").child(uids)
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val value = snapshot.child("Balance").value as String
                            if (value >= 1000.toString()) {
                                var databaseRefrence2 =
                                    FirebaseDatabase.getInstance().getReference("Bids")

                                var biddata = mapOf(
                                    "Amount" to number,
                                    "ProductName" to productName
                                )

                                val newRecordRef = databaseRefrence2.child(postid).child(uids)


                                newRecordRef.setValue(biddata)
                                Toast.makeText(
                                    getApplicationContext(),
                                    "Successfully!! Bid Placed",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }else
                            {
                                Toast.makeText(getApplicationContext(), "Your Balance is less than 1000 Plz Recharge Acoount", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Unit
                    }

                })
                var userdiplaybids = mapOf(

                    "Productname" to productName,
                    "Productimage" to productImage,
                    "Amount" to number

                )
                val databaseRefrences = FirebaseDatabase.getInstance().getReference("DisplayUserBids")

                databaseRefrences.child(uids).child("BiderBids").child(postid).child("Data")
                    .setValue(userdiplaybids)

            }


        }}
    }



    /* override fun onBackPressed() {
        val intent = Intent(this, ::class.java)
        startActivity(intent)
        super.onBackPressed()

    }
}*/