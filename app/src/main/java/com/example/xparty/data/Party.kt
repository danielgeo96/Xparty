package com.example.xparty.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parties")
data class Party(
    @ColumnInfo(name = "name")
    val partyName:String,
    @ColumnInfo(name = "description")
    val partyDescription:String,
    @ColumnInfo(name = "Location")
    val partyLocation:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}