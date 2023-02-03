package com.example.xparty.data.repository
import com.example.xparty.data.models.Party
import com.example.xparty.data.models.User
import com.example.xparty.data.local_db.PartiesDao
import com.example.xparty.data.local_db.UsersDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val partiesDao: PartiesDao,
private val usersDao: UsersDao) {

    fun getAllParties() = partiesDao.getAllParties()

    suspend fun addParty(party: Party){
        partiesDao.AddParty(party)
    }
    suspend fun deleteParty(party: Party)
    {
        partiesDao.deleteParty(party)
    }

    suspend fun  updateParty(party: Party)
    {
        partiesDao.updateParty(party)
    }

}