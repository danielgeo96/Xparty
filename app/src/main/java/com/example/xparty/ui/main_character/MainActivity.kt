package com.example.xparty.ui.main_character

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.xparty.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private var isOpen: Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Call findViewById on the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarDrawerToggle.syncState()


        // Call findViewById on the NavigationView
        navigationView = findViewById(R.id.navView)

        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mainFragmentStart_menu -> {
                    Toast.makeText(this, R.string.MainPage, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.user_events_history -> {
                    Toast.makeText(this, "Event history", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.user_favorites_events -> {
                    Toast.makeText(this, "Favorites events", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.producer_add_event -> {
                    Toast.makeText(this, "Add event", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.producer_history_events -> {
                    Toast.makeText(this, "My events", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }

        //TODO: Add support to shared pref after the creation of users
        setDrawerMenuItems(2)

    }

    override fun onSupportNavigateUp(): Boolean {
        if (isOpen) {
            drawerLayout.closeDrawer(navigationView)
            isOpen = false
            return false
        } else {
            drawerLayout.openDrawer(navigationView)
            isOpen = true
            return true
        }
    }

    private fun setDrawerMenuItems(index: Int) {
        var menu: Menu = navigationView.menu
        when (index) {
            0 -> {
                menu.removeGroup(R.id.userGroup)
                menu.removeGroup(R.id.producerGroup)
            }
            1 -> {
                menu.removeGroup(R.id.producerGroup)
            }
        }

    }
}