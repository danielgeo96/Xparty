package com.example.xparty.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xparty.data.Party


@Dao
interface PartiesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addParty(party: Party)

    @Delete
    fun deleteParty(party: Party)

    @Query("SELECT * FROM parties")
    fun getAllParties() : LiveData<List<Party>>
}