package com.example.aston_intensiv_finale_projekt.domain.episode

import android.content.Context
import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.CharacterApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.room.character.CharacterDao
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import kotlinx.coroutines.flow.Flow

interface EpisodeRepositoryInterface {
    fun getEpisodesFromApi(
        name: String, episode: String
    ): Flow<PagingData<EpisodeResultUI>>

    suspend fun getEpisodeById(id: Int): EpisodeResult
    suspend fun getCharactersByIds(ids: String): List<CharacterResult>
}