package com.example.xparty.data.repository.firebase

import com.example.xparty.data.repository.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class EventsRepositoryFirebaseModule {
    @Binds
    abstract fun EventsRepositoryFirebaseImpl(eventsRepositoryFirebase: EventsRepositoryFirebase):EventsRepository
}