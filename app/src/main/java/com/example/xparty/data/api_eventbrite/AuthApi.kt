package com.example.xparty.data.api_eventbrite

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AuthApi  {
    @GET("/v3/users/me/")
    suspend fun getUser(@Query("token")token:String) : Response<User>


}