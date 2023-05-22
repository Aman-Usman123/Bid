package com.example.bid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class registrationfragment : Fragment() {
private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var uri: Uri
    private var storageReference= Firebase.storage

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_registrationfragment, container, false)


                    firebaseAuth=FirebaseAuth.getInstance()
val btn =v.findViewById<Button>(R.id.registerbtn)
val login=v.findViewById<TextView>(R.id.logtext)
        storageReference= FirebaseStorage.getInstance()



        btn.setOnClickListener {

            val firstname = v.findViewById<EditText>(R.id.firstname)
            val lastname = v.findViewById<EditText>(R.id.lastname)
            val phones = v.findViewById<EditText>(R.id.phonenumber)
            val passwords = v.findViewById<EditText>(R.id.password).text.toString()
            val Emails = v.findViewById<EditText>(R.id.emailf).text.toString()
if(Emails.isNotEmpty()&& passwords.isNotEmpty())
{
firebaseAuth.createUserWithEmailAndPassword(Emails,passwords).addOnCompleteListener {

    if (it.isSuccessful) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {


                    val uid = FirebaseAuth.getInstance().currentUser!!.uid

                    val imageMap1 = mapOf(
                        "username" to firstname.text.toString(),
                        "cnicnumber" to lastname.text.toString(),
                        "phnumber" to phones.text.toString(),

                        )
                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference()
                    databaseReference.child(uid).child("Userinfo")
                        .setValue(imageMap1)

                } else {
                    Toast.makeText(requireContext(), "Sorry Try again", Toast.LENGTH_SHORT).show()
                }
            }

        Toast.makeText(requireContext(), "Go to Your Email Please", Toast.LENGTH_SHORT).show()


    } else {
        Toast.makeText(requireContext(), "The Email You enter is already taken", Toast.LENGTH_SHORT).show()
    }

}}
else
{
    Toast.makeText(requireContext(),"Email or password cannot be empty",Toast.LENGTH_SHORT).show()
}

        }
        login.setOnClickListener {
            val firstFragment = loginfragement()

            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.mainsss,firstFragment)?.commit()


        }



            return v }


}