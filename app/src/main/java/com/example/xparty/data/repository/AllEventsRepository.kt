package com.example.xparty.data.repository

import com.example.xparty.data.local_db.PartiesDao
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
        { localDataSource.insertParties(it._embedded.events) })
}