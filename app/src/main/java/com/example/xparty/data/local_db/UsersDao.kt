package com.example.xparty.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xparty.data.User


@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(user:User)

    @Delete
    fun  deleteUser(user: User)

    @Query("SELECT * FROM  Users")
    fun getAllUsers():LiveData<List<User>>
}