package com.example.xparty.data.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Event(
    val name: String = "",
    val description: String = "",
    var _embedded: VenuesEmbedded,
    var images: List<Image>,
    var id: String = ""
) {

}
