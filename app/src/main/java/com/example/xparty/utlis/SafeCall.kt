package com.example.xparty.utlis

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T>{
    return try{
        action()
    }catch (e:Exception){
        Resource.error(e.message ?: "An unknown error occurred")
    }
}