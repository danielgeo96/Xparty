package com.example.xparty.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "parties")
data class Party(

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val partyName: String ="",
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val partyDescription: String="",
    @ColumnInfo(name = "longitude")
    val longitude: Double = 0.0,
    @ColumnInfo(name = "latitude")
    val latitude: Double = 0.0,
    @ColumnInfo(name = "userId")
    val userId: String?="",
    @ColumnInfo(name = "isFav")
    var isFav: Boolean=false,
    @ColumnInfo(name = "image")
    var images : String = "",
    @PrimaryKey
    var id: String = ""
) {

}

