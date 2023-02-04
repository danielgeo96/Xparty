package com.example.xparty.ui.party_character

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPartyViewModel @Inject constructor(private val application: Application,private val eventsRepository: EventsRepository): ViewModel(){

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val userId : String = sharedPreferences.getString("userId","").toString()

    private val _addEventStatus = MutableLiveData<Resource<Void>>()
    val addEventStatus:LiveData<Resource<Void>> = _addEventStatus

    fun addEvent(title:String,description:String,location:String){
        viewModelScope.launch {
            _addEventStatus.postValue(Resource.loading())
            _addEventStatus.postValue(eventsRepository.addEvent(title,location,description,userId))
        }
    }
}