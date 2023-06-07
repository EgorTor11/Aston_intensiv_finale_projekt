package com.example.aston_intensiv_finale_projekt.domain.character

import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import kotlinx.coroutines.flow.Flow

interface CharacterRepositoryInterface{
    fun getCharactersFromApi(name: String, status: String,
                             species: String, gender: String): Flow<PagingData<CharacterResultUI>>
   suspend fun getCharacterById(id: Int): CharacterResult
   suspend fun getEpisodesByIds(ids: String): List<EpisodeResult>
}