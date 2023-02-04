package com.example.xparty.utlis
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationProvider @Inject
constructor(private val locationManager: LocationManager,
            private val permissionChecker: PremmsionChecker
            ):ViewModel() {


    private var locationLiveData = MutableLiveData<Location?>()
    val result: LiveData<Location?>
        get() = locationLiveData

    init {
        viewModelScope.launch {
            locationLiveData.value = getCurrentLocation()
            requestLocationUpdates()
        }
    }

    private fun requestLocationUpdates() {
        val locationListener = object : LocationListener {

            override fun onLocationChanged(p0: Location) {
                locationLiveData.value = p0
            }

            override fun onProviderEnabled(provider: String) {
                super.onProviderEnabled(provider)
                locationLiveData.value = getCurrentLocation()
            }
        }
        if (permissionChecker.checkPermission()) {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0f,
                    locationListener
                )
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    locationListener
                )
            } catch (e: SecurityException) {
                Log.e("LocationProvider", "Error requesting location updates", e)
            }
        }
    }

    private fun getCurrentLocation(): Location? {
        if (permissionChecker.checkPermission()) {
            return try {
                val lastKnownGPSLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val lastKnownNetworkLocation: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if ((lastKnownGPSLocation != null) && (lastKnownGPSLocation.time > (lastKnownNetworkLocation?.time
                        ?: 0))
                ) {
                    lastKnownGPSLocation
                } else {
                    lastKnownNetworkLocation
                }
            } catch (e: SecurityException) {
                null
            }
        } else {
            return null
        }
    }
}





