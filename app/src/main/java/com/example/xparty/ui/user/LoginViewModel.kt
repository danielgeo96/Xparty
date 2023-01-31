package com.example.xparty.ui.user

import androidx.lifecycle.*
import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import kotlinx.coroutines.launch

class LoginViewModel (private val authRepository: AuthRepository) : ViewModel() {

    private val _userSignInStatus = MutableLiveData<Resource<User>>()
    val userSignInStatus : LiveData<Resource<User>> = _userSignInStatus

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser : LiveData<Resource<User>> = _currentUser

    init{
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            _currentUser.postValue(authRepository.currentUser())
        }
    }

    fun signIn(email:String,password:String){
        _userSignInStatus.postValue(Resource.loading())
        viewModelScope.launch {
            val loginResult = authRepository.login(email,password)
            _userSignInStatus.postValue(loginResult)
        }
    }

    class LoginViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.NewInstanceFactory(){

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repo) as T
        }
    }
}