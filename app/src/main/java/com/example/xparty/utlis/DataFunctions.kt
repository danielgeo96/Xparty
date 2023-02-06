package com.example.xparty.utlis

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.xparty.data.models.Event
import com.example.xparty.data.models.Party
import kotlinx.coroutines.Dispatchers

fun <T, A> performFetchingAndSaving(
    localDbFetch: () -> LiveData<T>,
    remoteDbFetch: suspend () -> Resource<A>,
    localDbSave: suspend (A) -> Unit
): LiveData<Resource<T>> =

    liveData(Dispatchers.IO) {

        emit(Resource.loading())

        val source = localDbFetch().map { Resource.success(it) }
        emitSource(source)

        val fetchResource = remoteDbFetch()

        if (fetchResource.status is Success)

            localDbSave(fetchResource.status.data!!)
        else if (fetchResource.status is Error) {
            emit(Resource.error(fetchResource.status.message))
            emitSource(source)
        }
    }

//fun convertData(events : List<Event>):List<Party>{
//    val parties : ArrayList<Party> = ArrayList()
//    for(event in events){
//        var party = Party(event.name,event.description,event._embedded.venues.location.longitude,
//            event._embedded.venues.location.latitude,null,false,event.id)
//        parties.add(party)
//    }
//    return parties
//}