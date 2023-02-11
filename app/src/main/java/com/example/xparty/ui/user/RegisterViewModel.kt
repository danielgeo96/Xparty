package com.example.xparty.ui.user

import androidx.lifecycle.*
import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()
    val userRegistrationStatus: LiveData<Resource<User>> = _userRegistrationStatus

    fun createUser(
        userName: String,
        email: String,
        password: String,
        phone: String,
        photo: String,
        isProducer: Boolean
    ) {
        _userRegistrationStatus.postValue(Resource.loading())
        viewModelScope.launch {
            val registrationResult =
                repository.createUser(userName, email, password, phone, photo, isProducer)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }
}