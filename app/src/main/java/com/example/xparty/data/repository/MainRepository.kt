package com.example.xparty.data.repository
import com.example.xparty.data.Party
import com.example.xparty.data.User
import com.example.xparty.data.local_db.DataBase
import com.example.xparty.data.local_db.PartiesDao
import com.example.xparty.data.local_db.UsersDao
import javax.inject.Inject


class MainRepository @Inject constructor(dataBase: DataBase){
    private var partiesDao : PartiesDao?
    private  var usersDao : UsersDao?
    init {

        partiesDao = dataBase.PartiesDao()
        usersDao = dataBase.UsersDao()
    }
    fun getAllUsers() = usersDao?.getAllUsers()

    fun getAllParties() = partiesDao?.getAllParties()

   suspend fun addParty(party: Party){
        partiesDao?.AddParty(party)
    }
    suspend fun deleteParty(party: Party)
    {
        partiesDao?.deleteParty(party)
    }
    suspend fun  addUser(user: User)
    {
        usersDao?.addUser(user)
    }
    suspend fun  updateUser(user:User)
    {
        usersDao?.updateUser(user)
    }

    suspend fun  updateParty(party: Party)
    {
        partiesDao?.updateParty(party)
    }

    suspend fun  deleteUser(user: User)
    {
        usersDao?.deleteUser(user)
    }


}