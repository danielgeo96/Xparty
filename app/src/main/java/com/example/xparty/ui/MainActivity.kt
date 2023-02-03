package com.example.xparty.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.ui.user.LoginFragment
import com.example.xparty.ui.user.RegisterFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private var isOpen: Boolean = false
    lateinit var sharedPreferences: SharedPreferences
    private var isReadPermissionGranted = false
    private var isWritePermissionnGranted = false
    private var isInterntPermissionGranted = false
    private var isLoctionPermissionGranted = false
    private val permissionRequestLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        {
                permissions->
            isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]?:isReadPermissionGranted
            isLoctionPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION]?:isLoctionPermissionGranted
            isWritePermissionnGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]?:isWritePermissionnGranted
            isInterntPermissionGranted = permissions[Manifest.permission.ACCESS_NETWORK_STATE]?:isInterntPermissionGranted
        }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set always dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

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
            val navController:NavController = Navigation.findNavController(this,R.id.nav_host_fragment)

            when (it.itemId) {
                R.id.party_search_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.SearchFragment)
                    replaceFragment(it.title.toString())
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
                    navController.popBackStack()
                    navController.navigate(R.id.addPartyFragment)
                    replaceFragment(it.title.toString())
                    true
                }
                R.id.events_producer_history_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.myEventsFragment)
                    replaceFragment(it.title.toString())
                    true
                }
                R.id.login_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.LoginFragment)
                    replaceFragment(it.title.toString())
                    true
                }
                R.id.register_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.RegisterFragment)
                    replaceFragment(it.title.toString())
                    true
                }
                R.id.logout_btn -> {
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLogin", false).apply()
                    handleConnectionState()
                    true
                }
                else -> {
                    false
                }
            }
        }
        requestPermissions()
        handleConnectionState()


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

    private fun requestPermissions()
    {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        isLoctionPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        isWritePermissionnGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        isInterntPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
        val premissionRequestArray :MutableList<String> = ArrayList()
        if(!isReadPermissionGranted) premissionRequestArray.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if(!isLoctionPermissionGranted) premissionRequestArray.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if(!isWritePermissionnGranted) premissionRequestArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(!isInterntPermissionGranted) premissionRequestArray.add(Manifest.permission.ACCESS_NETWORK_STATE)
        if(premissionRequestArray.isNotEmpty()) permissionRequestLauncher.launch(premissionRequestArray.toTypedArray())


    }

    private fun setDrawerMenuItems(index: Int) {
        var menu: Menu = navigationView.menu
        when (index) {
            1 -> {
                menu.setGroupVisible(R.id.member_main, true)
                menu.setGroupVisible(R.id.producer_main, false)
                menu.setGroupVisible(R.id.guest, false)
                menu.setGroupVisible(R.id.member, true)
                return
            }
            2 -> {
                menu.setGroupVisible(R.id.member_main, true)
                menu.setGroupVisible(R.id.producer_main, true)
                menu.setGroupVisible(R.id.guest, false)
                menu.setGroupVisible(R.id.member, true)
                return
            }
            else -> {
                menu.setGroupVisible(R.id.member_main, false)
                menu.setGroupVisible(R.id.producer_main, false)
                menu.setGroupVisible(R.id.guest, true)
                menu.setGroupVisible(R.id.member, false)
                return
            }
        }
    }

    fun replaceFragment(title: String) {
        isOpen = false

        drawerLayout.closeDrawers()
        setTitle(title)
        val menu: Menu = navigationView.menu
        handleConnectionState()

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

    fun handleConnectionState() {
        var isLogin: Boolean = sharedPreferences.getBoolean("isLogin", false)
        var isProducer: Boolean = sharedPreferences.getBoolean("userType" , false)
        if (isLogin) {
            if (isProducer) {
                setDrawerMenuItems(2)
            } else {
                setDrawerMenuItems(1)
            }
        } else {
            setDrawerMenuItems(0)
        }
        setNavHeaderView()
    }

    private fun setNavHeaderView(){
        var view: View = navigationView.getHeaderView(0)
        var fullName : TextView = view.findViewById(R.id.full_name_header)
        var email: TextView = view.findViewById(R.id.email_header)
        var img : ImageView = view.findViewById(R.id.header_image_view)

        if(sharedPreferences.getBoolean("isLogin", false)){
            fullName.text = sharedPreferences.getString("fullName", null)
            email.text = sharedPreferences.getString("email",null)
            //TODO: ADD IMG SUPPORT
        }else{
            fullName.text = getString(R.string.Guest)
            email.text = getString(R.string.header_second_text)
            img.visibility = GONE
        }
    }



}