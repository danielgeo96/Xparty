package com.example.xparty.data.location

data class CurrentLocation(var latitude:Double,
                           var longitude:Double,
                           val countryName:String,
                           val locality:String,
                           val address:String)