package com.example.xparty.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.Party
import com.google.firebase.firestore.FirebaseFirestore

class EventsRepository {

    private var TAG: String = "EventsRepository"
    private lateinit var myEventsData: ArrayList<Party>
    private val instance: EventsRepository = EventsRepository()
    private lateinit var db: FirebaseFirestore
    private val myEventsLiveData: MutableLiveData<ArrayList<Party>> = MutableLiveData()

    public fun getMyEventsLiveData(): MutableLiveData<ArrayList<Party>> {
        return myEventsLiveData
    }

    public fun getInstance(): EventsRepository {
        return instance
    }

    public fun getMyEventsList(userId: String?) {
        myEventsData = ArrayList()
        db = FirebaseFirestore.getInstance()

        db.collection("events").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val party: Party = Party(
                        partyName = document.get("partyName").toString(),
                        partyLocation = document.get("partyLocation").toString(),
                        partyDescription = document.get("partyDescription").toString(),
                        userId = userId
                    )
                    myEventsData.add(party)
                }
                myEventsLiveData.postValue(myEventsData)
            }
    }
}