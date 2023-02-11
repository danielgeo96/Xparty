package com.example.xparty.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.xparty.data.models.User


@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM  Users")
    fun getAllUsers(): LiveData<List<User>>
}