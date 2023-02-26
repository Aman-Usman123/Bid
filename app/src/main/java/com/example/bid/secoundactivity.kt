package com.example.bid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class secoundactivity : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secoundactivity)
        val drawerLayout: DrawerLayout =findViewById(R.id.drwerlay)
        val navView: NavigationView =findViewById(R.id.navvieew)
        val bottomnavigationview:BottomNavigationView=findViewById(R.id.bottomNavigationView)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId)
            {R.id.home-> Toast.makeText(applicationContext,"Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.setting-> Toast.makeText(applicationContext,"Clicked Setting", Toast.LENGTH_SHORT).show()
                R.id.Sell-> Toast.makeText(applicationContext,"Clicked Refresh", Toast.LENGTH_SHORT).show()

            }
            true



        }
bottomnavigationview.setOnItemSelectedListener{

    when(it.itemId)
    {
R.id.Sell->replacefragment(sellfragments())

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

    private fun replacefragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.secoundactivity,fragment)
        fragmentTransaction.commit()

    }
 }
