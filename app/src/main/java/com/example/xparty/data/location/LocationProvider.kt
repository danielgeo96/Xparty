package com.example.xparty.data.location
import android.content.Context
import android.location.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LocationProvider @Inject constructor(private val locationManager :LocationManager,
                                           val locationViewModel:CurrentLocation):ViewModel() {
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

//    suspend fun getCurrentLocation():CurrentLocation {
//        if (isLocationEnabled()) {
//            val provider = locationManager.getBestProvider(Criteria(), false)
//            val location = locationManager.getLastKnownLocation(provider!!)
//            locationViewModel.longitude =location!!.latitude
//            locationViewModel.longitude = location!!.longitude
//        }
//        return  locationViewModel
//    }
}










