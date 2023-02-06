package com.example.xparty.data.repository

import com.example.xparty.data.local_db.PartiesDao
import com.example.xparty.data.models.Event
import com.example.xparty.data.models.Party
import com.example.xparty.data.remote_db.EventsRemoteDataSource
import com.example.xparty.utlis.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllEventsRepository @Inject constructor(
    private val remoteDataSource: EventsRemoteDataSource,
    private val localDataSource: PartiesDao,
) {

    fun getAllEvents() = performFetchingAndSaving(
        { localDataSource.getAllParties() },
        { remoteDataSource.getEvents() },
        { localDataSource.insertParties(convertData(it._embedded.events)) })
}

fun convertData(events : List<Event>):List<Party>{
    val parties : ArrayList<Party> = ArrayList()
    for(event in events){
        var party = Party(event.name,"",event._embedded.venues.get(0).location.longitude,
            event._embedded.venues.get(0).location.latitude,"",false,event.images.get(0).url,event.id)
        parties.add(party)
    }
    return parties
}