package com.example.xparty.ui.user

import androidx.lifecycle.*
import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import kotlinx.coroutines.launch

class RegisterViewModel constructor(private val repository: AuthRepository) : ViewModel(){

    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()
    val userRegistrationStatus : LiveData<Resource<User>> = _userRegistrationStatus

    fun createUser(userName : String, email:String, password: String,phone:String,isProducer:Boolean){
        _userRegistrationStatus.postValue(Resource.loading())
        viewModelScope.launch {
            val registrationResult = repository.createUser(userName,email,password,phone,isProducer)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }

    class RegisterViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.NewInstanceFactory(){

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(repo) as T
        }
    }
}