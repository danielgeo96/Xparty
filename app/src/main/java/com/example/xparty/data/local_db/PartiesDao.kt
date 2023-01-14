package com.example.xparty.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.xparty.data.Party


@Dao
interface PartiesDao {

    @Update
    suspend fun updateParty(party: Party)

    @Delete
    suspend fun deleteParty(party: Party)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddParty(party: Party)

    @Query("SELECT * FROM Parties")
    fun getAllParties() : LiveData<List<Party>>
}