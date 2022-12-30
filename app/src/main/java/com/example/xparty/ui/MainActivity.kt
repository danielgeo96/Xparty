package com.example.xparty.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.xparty.R
import com.example.xparty.ui.user.LoginFragment
import com.example.xparty.ui.user.RegisterFragment
import com.example.xparty.ui.main_character.PartySearchFragment
import com.example.xparty.ui.party_character.AddPartyFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private var isOpen: Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

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
                    val editor = sharedPreference.edit()
                    editor.putBoolean("isLogin", false)
                    editor.apply()
                    handleConnectionState(isLogin = false, type = false)

                    true
                }
                else -> {
                    false
                }
            }
        }

        handleConnectionState(
            sharedPreference.getBoolean("isLogin", false) as Boolean,
            sharedPreference.getBoolean("type", false) as Boolean
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        return if (isOpen) {
            drawerLayout.closeDrawer(navigationView)
            isOpen = false
            false
        } else {
            drawerLayout.openDrawer(navigationView)
            isOpen = true
            true
        }
    }

    private fun setDrawerMenuItems(index: Int) {
        var menu: Menu = navigationView.menu
        when (index) {
            1 -> {
                menu.setGroupVisible(R.id.member_main, true)
                menu.setGroupVisible(R.id.producer_main, false)
                menu.setGroupVisible(R.id.guest, false)
                menu.setGroupVisible(R.id.member, true)

            }
            2 -> {
                menu.setGroupVisible(R.id.member_main, true)
                menu.setGroupVisible(R.id.producer_main, true)
                menu.setGroupVisible(R.id.guest, false)
                menu.setGroupVisible(R.id.member, true)
            }
            else -> {
                menu.setGroupVisible(R.id.member_main, false)
                menu.setGroupVisible(R.id.producer_main, false)
                menu.setGroupVisible(R.id.guest, true)
                menu.setGroupVisible(R.id.member, false)

            }
        }
    }

    fun replaceFragment(fragment: Fragment, title: String) {
        isOpen = false
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
        val menu: Menu = navigationView.menu
        when (title) {
            getString(R.string.MainPage) -> {
                menu.getItem(0).isChecked = true
            }
            getString(R.string.event_history) -> {
                menu.getItem(1).isChecked = true
            }
            getString(R.string.favorites_events) -> {
                menu.getItem(2).isChecked = true
            }
            getString(R.string.add_event) -> {
                menu.getItem(3).isChecked = true
            }
            getString(R.string.my_events) -> {
                menu.getItem(4).isChecked = true
            }
            getString(R.string.login) -> {
                menu.getItem(5).isChecked = true
            }
            getString(R.string.register) -> {
                menu.getItem(6).isChecked = true
            }
            getString(R.string.logout) -> {
                menu.getItem(7).isChecked = true
            }
        }
    }

    fun handleConnectionState(isLogin: Boolean, type: Boolean) {
        if (isLogin) {
            if (type) {
                setDrawerMenuItems(2)
            } else {
                setDrawerMenuItems(1)
            }
        } else {
            setDrawerMenuItems(0)
        }
    }

}