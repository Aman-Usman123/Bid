package com.example.bid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
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
            {R.id.drawer_item_1->  {
                val fragment = Aboutapp()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drwerlay, fragment)
                    .commit()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
                R.id.drawer_item_2->{
                    val fragment = Userinforecycler()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.drwerlay, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true

                }

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

}