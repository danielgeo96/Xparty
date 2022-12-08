package com.example.xparty.data.Repository
import android.app.Application
import com.example.xparty.data.Party
import com.example.xparty.data.User
import com.example.xparty.data.local_db.DataBase
import com.example.xparty.data.local_db.PartiesDao
import com.example.xparty.data.local_db.UsersDao


class MainRepository(application: Application) {
    private var partiesDao : PartiesDao?
    private  var usersDao : UsersDao?
    init {
        val db = DataBase.getDataBase(application.applicationContext)
        partiesDao = db.PartiesDao()
        usersDao = db.UsersDao()
    }
    fun getAllUsers() = usersDao?.getAllUsers()
    fun getAllParties() = partiesDao?.getAllParties()

    fun addParty(party: Party){
        partiesDao?.addParty(party)
    }
    fun addUser(user: User){
        usersDao?.addUser(user)
    }
    fun deleteUser(user:User){
        usersDao?.deleteUser(user)
    }
    fun deleteParty(party: Party){
        partiesDao?.deleteParty(party)
    }


}