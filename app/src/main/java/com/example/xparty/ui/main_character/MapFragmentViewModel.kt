package com.example.xparty.ui.main_character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.AllEventsRepository
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor(
    allEventsRepository: AllEventsRepository,
    eventsRepository: EventsRepository
) : ViewModel() {

    val _eventsStatus: MutableLiveData<Resource<List<Party>>> = MutableLiveData()
    val eventsStatus: LiveData<Resource<List<Party>>> = _eventsStatus

    val events = allEventsRepository.getAllEvents()

    init {
        eventsRepository.getAllEventsLiveData(_eventsStatus)
    }
}
