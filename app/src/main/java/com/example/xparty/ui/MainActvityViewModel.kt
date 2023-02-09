package com.example.xparty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActvityViewModel @Inject constructor(private val authRepository: AuthRepository):
    ViewModel() {
    private val _currentUser = MutableLiveData<Resource<User>>()
    val userSignInStatus : LiveData<Resource<User>> = _currentUser

    init {

        viewModelScope.launch{
            _currentUser.postValue(Resource.loading())
            _currentUser.postValue(authRepository.currentUser())
        }
    }

    fun updateUser(user: User)
    {
        viewModelScope.launch{
            val updateResult = authRepository.updateUser(user)
            _currentUser.postValue(updateResult)
        }
    }


}