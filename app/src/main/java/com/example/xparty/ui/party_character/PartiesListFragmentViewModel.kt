package com.example.xparty.ui.party_character

import androidx.lifecycle.*
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.FavoritesEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesListFragmentViewModel @Inject constructor(
    favoritesEventsRepository: FavoritesEventsRepository
) : ViewModel() {

    private val instance = favoritesEventsRepository

    fun setEvent(event: Party) {
        viewModelScope.launch {
            instance.setFavParty(event)
        }
    }

}