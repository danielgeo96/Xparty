package com.example.xparty.ui.user_character

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CurrentLocationViewModel(application: Application):
    AndroidViewModel(application) {
        val location : LiveData<String> = LocationUpdatesLiveData(application.applicationContext)

}