package com.example.xparty.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.models.Party
import com.example.xparty.utlis.Resource
import javax.inject.Singleton

interface EventsRepository {

    suspend fun addEvent(
        title: String, longitude: Double,
        latitude: Double, description: String,userId: String,isFav:Boolean,img:String
    ): Resource<Void>

    suspend fun deleteEvent(eventId: String): Resource<Void>
    suspend fun getEvent(id: String): Resource<Party>
    fun getAllEventsLiveData(data: MutableLiveData<Resource<List<Party>>>)
    fun getMyEventsLiveData(data: MutableLiveData<Resource<List<Party>>>, userId: String)
}