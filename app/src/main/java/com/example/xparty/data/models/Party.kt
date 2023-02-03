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
    @SerializedName("name")
    @ColumnInfo(name = "description")
    val partyDescription: String="",
    @SerializedName("name")
    @ColumnInfo(name = "Location")
    val partyLocation: String="",
    @SerializedName("name")
    @ColumnInfo(name = "userId")
    val userId: String?="",
    @SerializedName("name")
    @ColumnInfo(name = "eventId")
    val eventId: String?=""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}

