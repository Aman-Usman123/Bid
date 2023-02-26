package com.example.bid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class startupapp : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val v=inflater.inflate(R.layout.fragment_startupapp, container, false)
        val button=v.findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_startupapp_to_loginfragement2)


        }
        return v
    }


}