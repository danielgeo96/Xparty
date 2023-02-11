package com.example.xparty.ui.user

import androidx.lifecycle.*
import com.example.xparty.data.models.User
import com.example.xparty.data.repository.AuthRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _userSignInStatus = MutableLiveData<Resource<User>>()
    val userSignInStatus: LiveData<Resource<User>> = _userSignInStatus

    private val _currentUser = MutableLiveData<Resource<User>>()

    init {
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            _currentUser.postValue(authRepository.currentUser())
        }
    }

    fun signIn(email: String, password: String) {
        _userSignInStatus.postValue(Resource.loading())
        viewModelScope.launch {
            val loginResult = authRepository.login(email, password)
            _userSignInStatus.postValue(loginResult)
        }
    }

}