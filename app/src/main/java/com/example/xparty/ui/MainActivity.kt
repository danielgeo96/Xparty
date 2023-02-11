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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.xparty.R
import com.example.xparty.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private var isOpen: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private var isInternetPermissionGranted = false
    private var isLocationPermissionGranted = false
    private val permissionRequestLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            isReadPermissionGranted =
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isLocationPermissionGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
            isWritePermissionGranted =
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
            isInternetPermissionGranted =
                permissions[Manifest.permission.ACCESS_NETWORK_STATE] ?: isInternetPermissionGranted
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set always dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

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
            val navController: NavController =
                Navigation.findNavController(this, R.id.nav_host_fragment)

            when (it.itemId) {
                R.id.party_search_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.SearchFragment)
                    replaceFragment(it.title.toString())
                    true
                }
                R.id.favorites_events_btn -> {
                    navController.popBackStack()
                    navController.navigate(R.id.favoritesEventsFragment)
                    replaceFragment(it.title.toString())
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

    private fun requestPermissions() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        isWritePermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isInternetPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED
        val permissionRequestArray: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) permissionRequestArray.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!isLocationPermissionGranted) permissionRequestArray.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (!isWritePermissionGranted) permissionRequestArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isInternetPermissionGranted) permissionRequestArray.add(Manifest.permission.ACCESS_NETWORK_STATE)
        if (permissionRequestArray.isNotEmpty()) permissionRequestLauncher.launch(
            permissionRequestArray.toTypedArray()
        )
    }

    private fun setDrawerMenuItems(index: Int) {
        val menu: Menu = navigationView.menu
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
            getString(R.string.favorites_events) -> {
                menu.getItem(1).isChecked = true
            }
            getString(R.string.add_event) -> {
                menu.getItem(2).isChecked = true
            }
            getString(R.string.my_events) -> {
                menu.getItem(3).isChecked = true
            }
            getString(R.string.login) -> {
                menu.getItem(4).isChecked = true
            }
            getString(R.string.register) -> {
                menu.getItem(5).isChecked = true
            }
            getString(R.string.logout) -> {
                menu.getItem(6).isChecked = true
            }
        }
    }

    private fun handleConnectionState() {
        val isLogin: Boolean = sharedPreferences.getBoolean("isLogin", false)
        val isProducer: Boolean = sharedPreferences.getBoolean("producer", false)
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

    private fun setNavHeaderView() {
        val view: View = navigationView.getHeaderView(0)
        val fullName: TextView = view.findViewById(R.id.full_name_header)
        val email: TextView = view.findViewById(R.id.email_header)
        val img: ImageView = view.findViewById(R.id.header_image_view)
        if (sharedPreferences.getBoolean("isLogin", false)) {
            fullName.text = sharedPreferences.getString("fullName", null)
            email.text = sharedPreferences.getString("email", null)
            val imageUriString = sharedPreferences.getString("imageUri", null)
            Glide.with(this).load(imageUriString?.toUri()).placeholder(R.drawable.profile_image).into(img)
        } else {
            fullName.text = getString(R.string.Guest)
            email.text = getString(R.string.header_second_text)
        }
    }

}