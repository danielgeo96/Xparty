package com.example.xparty.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.xparty.data.Party
import com.example.xparty.data.User
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartiesUsersViewModel @Inject constructor(val mainRepository: MainRepository,
                                                application: Application):AndroidViewModel(application){

      val parties : LiveData<List<Party>>? = mainRepository.getAllParties()
      val users :LiveData<List<User>>? = mainRepository.getAllUsers()


    fun addUser(user: User)
    {
        viewModelScope.launch {
            mainRepository.addUser(user)
        }
    }

    fun addParty(party: Party)
    {
        viewModelScope.launch {
            mainRepository.addParty(party)
        }
    }

    fun deleteParty(party: Party)
    {
        viewModelScope.launch {
            mainRepository.deleteParty(party)
        }
    }

    fun deleteUser(user: User)
    {
        viewModelScope.launch {
            mainRepository.deleteUser(user)
        }
    }

    fun updateUser(user:User)
    {
        viewModelScope.launch{
            mainRepository.updateUser(user)
        }
    }

    fun updateParty(party: Party)
    {
        viewModelScope.launch{
            mainRepository.updateParty(party)
        }
    }


}