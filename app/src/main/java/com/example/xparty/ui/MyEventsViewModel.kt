package com.example.xparty.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.Party
import com.example.xparty.data.repository.EventsRepository

class MyEventsViewModel(application: Application) : AndroidViewModel(application) {

//    private var data: MutableLiveData<ArrayList<Party>> =
//        EventsRepository().getInstance().getMyEventsLiveData()
//    private val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences("pref",Context.MODE_PRIVATE)
//
//    public fun MyEventsViewModel(@NonNull application: Application){
//        super(application)
//    }
//
//    public fun getMyEvents():LiveData<ArrayList<Party>>{
//        return data
//    }
//
//    public fun getMyEventsFromDB(){
//        EventsRepository().getInstance().getMyEventsList(sharedPreferences.getString("userId",null))
//    }
}