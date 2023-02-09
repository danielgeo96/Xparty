package com.example.xparty.ui.party_character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.FavoritesEventsRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesEventsFragmentViewModel @Inject constructor(
    favoritesEventsRepository: FavoritesEventsRepository
) : ViewModel() {

    private val instance = favoritesEventsRepository

    val _eventsStatus : MutableLiveData<Resource<List<Party>>> = MutableLiveData()
    val eventsStatus : LiveData<Resource<List<Party>>> = _eventsStatus

    init {
        viewModelScope.launch {
            instance.getFavParties(_eventsStatus)
        }
    }

    fun removeFavEvent(event:Party){
        viewModelScope.launch {
            instance.removeFavParty(event)
        }
    }
}