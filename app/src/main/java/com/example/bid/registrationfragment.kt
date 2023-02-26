package com.example.bid

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


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
    {
Toast.makeText(requireContext(),"Register Successfully",Toast.LENGTH_SHORT).show()
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



            return v }


}