package com.example.aston_intensiv_finale_projekt.domain.location

import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI
import kotlinx.coroutines.flow.Flow

interface LocationRepositoryInterface {
    fun getLocationsFromApi(
        name: String, type: String,dimension:String
    ): Flow<PagingData<LocationResultUI>>

    suspend fun getLocationById(id: Int): LocationResult
    suspend fun getCharactersByIds(ids: String): List<CharacterResult>
}