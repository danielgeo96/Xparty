package com.example.xparty.data.api_eventbrite

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(userRemoteDataSource: UserRemoteDataSource):ViewModel() {
    private val myLiveData =MutableLiveData<String?>()
    val user:LiveData<String?>
        get() = myLiveData
    init {
        viewModelScope.launch {
            val result = userRemoteDataSource.getUser("J6KTI2QR5XX5NFBHCIQU")
            val rs = result.status.data!!
            when(result.status){
                is Loading-> myLiveData.value  ="Loading"
                is Success -> {
                    if(result.status.data != null )
                    {
                        myLiveData.value = "Secusses"
                    }
                }
                is Error -> {
                    myLiveData.value = "Errpr"
                }

            }

        }

    }


}