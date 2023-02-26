package com.example.bid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

class loginfragement : Fragment() {

private lateinit var firebaseAuth:FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view= inflater.inflate(R.layout.fragment_loginfragement, container, false)
        val register=view.findViewById<TextView>(R.id.registertext)
        register.setOnClickListener {
            findNavController().navigate(R.id.action_loginfragement_to_registrationfragment)



        }
        firebaseAuth=FirebaseAuth.getInstance()
        val login=view.findViewById<Button>(R.id.login1)
        login.setOnClickListener {
            val passwords = view.findViewById<EditText>(R.id.passwordlogin).text.toString()
            val Emails = view.findViewById<EditText>(R.id.email1).text.toString()

            if(Emails.isNotEmpty()&& passwords.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(Emails,passwords).addOnCompleteListener {

                    if(it.isSuccessful)
                    {
                        val i=Intent(this@loginfragement.requireContext(),DatabaseRecycler::class.java)
                        startActivity(i)
                    }
                      else
                    {
                        Toast.makeText(requireContext(),"Wrong password or email",Toast.LENGTH_SHORT).show()
                    }
                    }


        }
        else
            {
                Toast.makeText(requireContext(),"Email or password cannot be empty",Toast.LENGTH_SHORT).show()
            }}


        return view
    }


}