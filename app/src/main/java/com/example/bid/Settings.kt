package com.example.bid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Settings : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drwerlay)
        val navView = findViewById<NavigationView>(R.id.navvieew)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {

            when(it.itemId)
            {R.id.drawer_item_1-> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.drawer_item_2-> Toast.makeText(applicationContext,"Clicked Setting", Toast.LENGTH_SHORT).show()

            }
            true

    }
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        // Start the new activity
        val intent = Intent(this, DatabaseRecycler::class.java)
        startActivity(intent)

        // Call super.onBackPressed() to exit the current activity
        super.onBackPressed()
    }}