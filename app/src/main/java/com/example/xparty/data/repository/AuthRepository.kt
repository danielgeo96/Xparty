package com.example.xparty.data.repository

import com.example.xparty.data.models.User
import com.example.xparty.utlis.Resource

interface AuthRepository {

    suspend fun currentUser() : Resource<User>
    suspend fun updateUser(user:User) :Resource<User>
    suspend fun login(email : String,password:String) : Resource<User>
    suspend fun createUser(userName: String,email: String,password: String,phone: String, isProducer:Boolean) : Resource<User>
    fun logOut()
}