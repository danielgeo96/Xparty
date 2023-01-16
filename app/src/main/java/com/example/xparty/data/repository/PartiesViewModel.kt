package com.example.xparty.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xparty.data.Party
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesViewModel @Inject constructor( private val mainRepository: MainRepository
) :ViewModel() {

    fun getAllParties() : LiveData<List<Party>> = mainRepository.getAllParties()

    fun addParty(party: Party)
    {
        viewModelScope.launch{
            mainRepository.addParty(party)
        }
    }
    fun updateParty(party: Party)
    {
        viewModelScope.launch{
            mainRepository.updateParty(party)
        }
    }

    fun deleteParty(party: Party)
    {
        viewModelScope.launch{
            mainRepository.deleteParty(party)
        }
    }
}