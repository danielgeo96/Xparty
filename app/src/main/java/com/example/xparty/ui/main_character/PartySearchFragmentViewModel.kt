package com.example.xparty.ui.main_character

import androidx.lifecycle.ViewModel
import com.example.xparty.data.repository.AllEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PartySearchFragmentViewModel @Inject constructor(
    allEventsRepository: AllEventsRepository
) :ViewModel() {

    val events = allEventsRepository.getAllEvents()
}