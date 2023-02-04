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
    @ColumnInfo(name = "Location")
    val partyLocation: String="",
    @ColumnInfo(name = "userId")
    val userId: String?="",
    @ColumnInfo(name = "isFav")
    var isFav: Boolean=false,
    @PrimaryKey
    var id: String = ""
) {
}

