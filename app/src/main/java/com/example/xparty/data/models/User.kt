package com.example.xparty.data.models

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class User(
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "phone")
    val phone: String = "",
    @ColumnInfo(name = "producer")
    val producer: Boolean = false,
    @ColumnInfo(name = "photo")
    val photo: String = "",
    @ColumnInfo(name = "userId")
    val userId: String = "",
) {
    @PrimaryKey
    var id: String = ""


}