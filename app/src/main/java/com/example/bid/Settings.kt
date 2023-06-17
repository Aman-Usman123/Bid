package com.example.bid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
private const val TAG="Settings"
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
        setupNavigationView()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {menuItem->



            when(menuItem.itemId)
            {R.id.drawer_item_1->  {
                val fragment = Aboutapp()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
                R.id.drawer_item_2->{
                    val fragment = Userinforecycler()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true

                }
                R.id.drawer_item_3->{
                   performLogout()

                }
            }
            true

    }
}


    private fun performLogout() {
        // Create a dialog to confirm logout
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Logout")
        dialogBuilder.setMessage("Are you sure you want to logout?")
        dialogBuilder.setIcon(R.drawable.logo)

        // Set positive button click listener
        dialogBuilder.setPositiveButton("Logout") { _, _ ->
            // Perform logout actions
            // For example, clear user session, navigate to login screen, etc.
            clearUserSession()
            navigateToLoginScreen()
        }

        // Set negative button click listener
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            // Dismiss the dialog and do nothing
            dialog.dismiss()
        }

        // Show the dialog
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun clearUserSession() {
        // Clear user session or perform any necessary logout actions
        // For example, clear user credentials from shared preferences
        val sharedPreferences = getSharedPreferences("YourPreferencesName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun navigateToLoginScreen() {
        // Navigate to the login screen
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish the current activity
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

        private fun setupNavigationView() {
            val navigationView = findViewById<NavigationView>(R.id.navvieew)

            val headerView = navigationView.getHeaderView(0)

            val databaseReference = FirebaseDatabase.getInstance().reference
            val uid=FirebaseAuth.getInstance().currentUser!!.uid
            databaseReference.child("Registration_Record").child(uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                @SuppressLint("MissingInflatedId")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(usserImages::class.java)
                        Log.d(TAG,"Name${user}")

                        val headerTitleTextView = headerView.findViewById<TextView>(R.id.menu_drawer_title)
                        if (user != null) {
                            headerTitleTextView.text = user?.username
                            Log.d(TAG,"Name===${headerTitleTextView}")
                        }


                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors
                }
            })
    }


}