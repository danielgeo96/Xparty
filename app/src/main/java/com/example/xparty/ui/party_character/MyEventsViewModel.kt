package com.example.xparty.ui.party_character

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyEventsViewModel@Inject constructor(private val eventsRepository: EventsRepository,
                                           private val application: Application) : ViewModel() {

    private val sharedPreferences:SharedPreferences  = application.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val userId : String = sharedPreferences.getString("userId","").toString()

    val _eventsStatus : MutableLiveData<Resource<List<Party>>> = MutableLiveData()
    val eventsStatus : LiveData<Resource<List<Party>>> = _eventsStatus

    private val _deleteEvent = MutableLiveData<Resource<Void>>()
    val deleteEvent:LiveData<Resource<Void>> = _deleteEvent

    fun deleteEvent(id:String){
        viewModelScope.launch {
            _deleteEvent.postValue(Resource.loading())
            _deleteEvent.postValue(eventsRepository.deleteEvent(id))
        }
    }

    init {
        eventsRepository.getMyEventsLiveData(_eventsStatus,userId)
    }

}