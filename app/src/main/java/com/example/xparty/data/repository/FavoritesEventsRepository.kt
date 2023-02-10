package com.example.xparty.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.xparty.data.local_db.PartiesDao
import com.example.xparty.data.models.Party
import com.example.xparty.utlis.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesEventsRepository @Inject constructor(
    private val localDataSource: PartiesDao
) {

    suspend fun getFavParties(data: MutableLiveData<Resource<List<Party>>>) {
        withContext(Dispatchers.IO){
            data.postValue(Resource.loading())
            data.postValue(Resource.success(localDataSource.getAllFavParties()))
        }
    }

    suspend fun setFavParty(event: Party) {
        withContext(Dispatchers.IO){
            event.isFav = true
            localDataSource.AddParty(event)
        }
    }

    suspend fun removeFavParty(event: Party) {
        withContext(Dispatchers.IO){
            event.isFav = false
            localDataSource.updateParty(event)
        }
    }


}