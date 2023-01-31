package com.example.xparty.utlis
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationProvider @Inject
constructor(private val locationManager: LocationManager,
            private val premmsionChecker: PremmsionChecker
            ):ViewModel() {



    var result = MutableLiveData<Location?>()
    init {
        result.value = getCurrentLocation()
    }

    private fun getCurrentLocation(): Location? {
        if(premmsionChecker.checkPermission())
        {
            val lastKnownGPSLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val lastKnownNetworkLocation: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if ((lastKnownGPSLocation != null) && (lastKnownGPSLocation.time > (lastKnownNetworkLocation?.time
                    ?: 0))
            ) {
                return lastKnownGPSLocation
            } else {
                return lastKnownNetworkLocation
            }
        }
        else
        {
            return null
        }
    }


}