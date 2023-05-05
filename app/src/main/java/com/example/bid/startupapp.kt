package com.example.bid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager

class startupapp : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_startupapp, container, false)
        val button = v.findViewById<Button>(R.id.getstarted)
        button.setOnClickListener {
            val firstFragment = loginfragement()

            val transaction = fragmentManager?.beginTransaction()
transaction?.replace(R.id.mainsss,firstFragment)?.commit()


        }
        return v
    }
}