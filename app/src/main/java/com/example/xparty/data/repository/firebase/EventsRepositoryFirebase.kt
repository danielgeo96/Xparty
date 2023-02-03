package com.example.xparty.data.repository.firebase

import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import com.example.xparty.utlis.safeCall
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EventsRepositoryFirebase : EventsRepository {

    private val eventsRef = FirebaseFirestore.getInstance().collection("events")

    override suspend fun addEvent(
        title: String,
        location: String,
        description: String,
        userId: String
    ): Resource<Void> =
        withContext(Dispatchers.IO) {
            safeCall {
                val eventId = eventsRef.document().id
                val event = Party(title, description, location,userId, eventId)
                val addition = eventsRef.document(eventId).set(event).await()
                Resource.success(addition)
            }
        }

    override suspend fun deleteEvent(eventId: String) = withContext(Dispatchers.IO) {
        safeCall {
            val result = eventsRef.document(eventId).delete().await()
            Resource.success(result)
        }
    }

    override suspend fun getEvent(id: String) = withContext(Dispatchers.IO) {
        safeCall {
            val result = eventsRef.document(id).get().await()
            val event = result.toObject(Party::class.java)
            Resource.success(event!!)
        }
    }

    override fun getAllEventsLiveData(data: MutableLiveData<Resource<List<Party>>>) {
        data.postValue(Resource.loading())

        eventsRef.orderBy("partyName").addSnapshotListener { snapshot, e ->
            if (e != null) {
                data.postValue(Resource.error(e.localizedMessage))
            }
            if (snapshot != null && !snapshot.isEmpty){
                data.postValue(Resource.success(snapshot.toObjects(Party::class.java)))
            }
            else{
                data.postValue(Resource.error("No Data"))
            }
        }
    }

    override fun getMyEventsLiveData(data: MutableLiveData<Resource<List<Party>>>,userId:String) {
        data.postValue(Resource.loading())

        eventsRef.whereEqualTo("userId",userId).orderBy("partyName").addSnapshotListener { snapshot, e ->
            if (e != null) {
                data.postValue(Resource.error(e.localizedMessage))
            }
            if (snapshot != null && !snapshot.isEmpty){
                data.postValue(Resource.success(snapshot.toObjects(Party::class.java)))
            }
            else{
                data.postValue(Resource.error("No Data"))
            }
        }
    }
}