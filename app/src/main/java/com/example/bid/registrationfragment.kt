package com.example.bid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class registrationfragment : Fragment() {
private lateinit var firebaseAuth: FirebaseAuth




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
        btn.setOnClickListener {

            val firstname = v.findViewById<EditText>(R.id.firstname).text.toString()
            val lastname = v.findViewById<EditText>(R.id.lastname).text.toString()
            val phones = v.findViewById<EditText>(R.id.phonenumber).text.toString()
            val passwords = v.findViewById<EditText>(R.id.password).text.toString()
            val Emails = v.findViewById<EditText>(R.id.emailf).text.toString()
if(Emails.isNotEmpty()&& passwords.isNotEmpty())
{
firebaseAuth.createUserWithEmailAndPassword(Emails,passwords).addOnCompleteListener {

    if(it.isSuccessful)
    {val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Email sent successfully
                    Toast.makeText(requireContext(),"Go to Your Email Please",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),"Sorry Try again",Toast.LENGTH_SHORT).show()
                }
            }


    }else
    {
        Toast.makeText(requireContext(),"Error occurs Not registered",Toast.LENGTH_SHORT).show()
    }
}

}else
{
    Toast.makeText(requireContext(),"Email or password cannot be empty",Toast.LENGTH_SHORT).show()
}

        }
        login.setOnClickListener {


                findNavController().navigate(R.id.action_registrationfragment_to_loginfragement)

        }



            return v }


}