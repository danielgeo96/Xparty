package com.example.xparty.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.models.Party
import com.example.xparty.utlis.Resource

interface EventsRepository {

    suspend fun addEvent(title:String,location:String,description:String,userId: String) : Resource<Void>
    suspend fun deleteEvent(eventId: String): Resource<Void>
    suspend fun getEvent(id:String) : Resource<Party>
    fun getAllEventsLiveData(data : MutableLiveData<Resource<List<Party>>>)
    fun getMyEventsLiveData(data : MutableLiveData<Resource<List<Party>>>,userId:String)
}