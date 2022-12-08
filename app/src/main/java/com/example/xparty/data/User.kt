package com.example.xparty.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class User(
    @ColumnInfo(name = "name")
    val name:String
    ,@ColumnInfo(name = "password")
    val password:String,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "photo")
    val photo:String?){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}