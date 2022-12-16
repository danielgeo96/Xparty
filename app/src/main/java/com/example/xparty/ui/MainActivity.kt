package com.example.xparty.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.ui.login_and_register.LoginFragment
import com.example.xparty.ui.login_and_register.RegisterFragment
import com.example.xparty.ui.main_character.PartySearchFragment
import com.example.xparty.ui.party_character.AddPartyFragment
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
        navigationView.setNavigationItemSelectedListener {

            it.isChecked = true

            when (it.itemId) {
                R.id.party_search_btn -> {
                    //view?.findNavController()?.navigate(R.id.action_mainFragmentStart_to_mapFragment)
                    replaceFragment(PartySearchFragment(), it.title.toString())
                    true
                }
                R.id.events_user_history_btn -> {
                    Toast.makeText(this, "Event history", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.favorites_events_btn -> {
                    Toast.makeText(this, "Favorites events", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_event_btn -> {
                    replaceFragment(AddPartyFragment(), it.title.toString())
                    true
                }
                R.id.events_producer_history_btn -> {
                    Toast.makeText(this, "My events", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.login_btn -> {
                    replaceFragment(LoginFragment(), it.title.toString())
                    true
                }
                R.id.register_btn -> {
                    replaceFragment(RegisterFragment(), it.title.toString())
                    true
                }
                R.id.logout_btn -> {
                    replaceFragment(PartySearchFragment(), it.title.toString())
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
            1 -> {
                menu.removeItem(R.id.add_event_btn)
                menu.removeItem(R.id.events_producer_history_btn)
                menu.removeItem(R.id.guest)
            }
            2 -> {
                menu.removeItem(R.id.guest)
            }
            else->{
                menu.removeItem(R.id.favorites_events_btn)
                menu.removeItem(R.id.add_event_btn)
                menu.removeItem(R.id.events_user_history_btn)
                menu.removeItem(R.id.events_producer_history_btn)
                menu.removeItem(R.id.member)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
}