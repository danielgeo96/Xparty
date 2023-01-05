package com.example.xparty.ui.user_character

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY


class LocationUpdatesLiveData(context:Context):LiveData<String>() {

    private val locationClient:FusedLocationProviderClient
        =LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest =LocationRequest.Builder(PRIORITY_BALANCED_POWER_ACCURACY,20)

    private val locationCallBack = object:LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            p0.lastLocation?.let {
                postValue("${it.latitude},${it.longitude}")
            }
        }
    }
    override fun onActive() {
        super.onActive()
        try {
            locationClient.requestLocationUpdates(
                locationRequest.build(),
                locationCallBack,
                Looper.getMainLooper()
            )
        }catch (e:SecurityException)
        {
            Log.d("LocationUpdatesLiveData","Missing location")
        }


    }

    override fun onInactive() {
        super.onInactive()
        locationClient.removeLocationUpdates(locationCallBack)
    }
}