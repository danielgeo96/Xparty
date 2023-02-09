package com.example.xparty.data.remote_db
import com.example.xparty.data.models.AllEvents
import retrofit2.Response
import retrofit2.http.GET

interface EventsService {

    @GET("events.json?apikey=TWwSaM3BIZmc2x0byoQUv8hGhblqK1Hz")
    suspend fun getAllEvents() : Response<AllEvents>

}