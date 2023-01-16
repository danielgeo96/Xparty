package com.example.xparty.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xparty.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor( private val mainRepository: MainRepository)
    :ViewModel() {
    fun getAllUsers() :LiveData<List<User>> = mainRepository.getAllUsers()

    fun addUser(user: User)
    {
        viewModelScope.launch{
            mainRepository.addUser(user)
        }
    }
    fun updateUser(user: User)
    {
        viewModelScope.launch{
            mainRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User)
    {
        viewModelScope.launch{
            mainRepository.deleteUser(user)
        }
    }


}