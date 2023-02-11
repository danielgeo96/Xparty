package com.example.xparty.data.remote_db

import javax.inject.Inject

class EventsRemoteDataSource @Inject constructor(
    private val eventsService: EventsService
) : BaseDataSource() {

    suspend fun getEvents() = getResult { eventsService.getAllEvents() }
}