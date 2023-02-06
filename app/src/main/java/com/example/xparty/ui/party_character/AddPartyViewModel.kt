package com.example.xparty.ui.party_character

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import kotlinx.coroutines.launch

class AddPartyViewModel(application: Application,private val eventsRepository: EventsRepository): AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val userId : String = sharedPreferences.getString("userId","").toString()


    private val _addEventStatus = MutableLiveData<Resource<Void>>()
    val addEventStatus:LiveData<Resource<Void>> = _addEventStatus

    fun addEvent(title:String,description:String,longitude: Double,
                 latitude:Double,isFav:Boolean,img:String){
        viewModelScope.launch {
            _addEventStatus.postValue(Resource.loading())
            _addEventStatus.postValue(eventsRepository.addEvent(title,longitude,
                latitude,description,userId,isFav,img))
        }
    }

    class AddPartyViewModelFactory(val eventsRepository: EventsRepository,val application: Application) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddPartyViewModel(application,eventsRepository) as T
        }
    }
}