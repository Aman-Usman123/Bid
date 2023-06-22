package com.example.bid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class playing : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_playing)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val firstFragment = Databaserecyclerr()
        fragmentTransaction.add(R.id.playing, firstFragment)
        fragmentTransaction.commit()

        val bottomnavigationview: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomnavigationview.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.Sell -> {
                    val fragment1 = sells()
                    loadFragment(fragment1)
                    return@setOnItemSelectedListener true
                }
                R.id.home -> {

                    val fragment2 =Databaserecyclerr()
                    loadFragment(fragment2)
                    return@setOnItemSelectedListener true
                }
                R.id.setting -> {
                    startActivity(Intent(this, Settings::class.java))
                }
            }
            true
}

    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.playing, fragment)
            .commit()
    }
 }