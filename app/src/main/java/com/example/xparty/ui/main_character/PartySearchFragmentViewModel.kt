package com.example.xparty.ui.main_character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PartySearchFragmentViewModel @Inject constructor(
    eventsRepository: EventsRepository
) : ViewModel() {

    private val _eventsStatus: MutableLiveData<Resource<List<Party>>> = MutableLiveData()

    init {
        eventsRepository.getAllEventsLiveData(_eventsStatus)
    }
}