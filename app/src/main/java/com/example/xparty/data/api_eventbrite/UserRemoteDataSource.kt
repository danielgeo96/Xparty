package com.example.xparty.data.api_eventbrite

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRemoteDataSource @Inject
constructor(private val api: AuthApi):BaseDataSource() {
    suspend fun getUser(token:String) = getResult {  api.getUser(token) }

}