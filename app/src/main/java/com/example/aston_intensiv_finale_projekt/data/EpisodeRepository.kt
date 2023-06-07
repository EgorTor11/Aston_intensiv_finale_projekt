package com.example.aston_intensiv_finale_projekt.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensiv_finale_projekt.data.mappers.EpisodeMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterLocation
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterOrigin
import com.example.aston_intensiv_finale_projekt.data.paging.episode.EpisodeRemoteMediator
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.EpisodeApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.room.episode.EpisodeDao
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EpisodeRepository(
    val episodeApi: EpisodeApi,
    val episodeDao: EpisodeDao,
    val context: Context
):EpisodeRepositoryInterface {
    @OptIn(ExperimentalPagingApi::class)
    override fun getEpisodesFromApi(
        name: String, episode: String
    ): Flow<PagingData<EpisodeResultUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = true,
                initialLoadSize = 20
            ),
            remoteMediator = EpisodeRemoteMediator(
                episodeDao,
                episodeApi,
                name,
                episode,
            ),
            pagingSourceFactory = { episodeDao.getPagingSource(name, episode) }
        ).flow.map { it.map { EpisodeMapper().mapEpisodeResultDbForEpisodeResultUI(it) } }
    }

    override suspend fun getEpisodeById(id: Int): EpisodeResult {
        try {
            val response = episodeApi.getEpisodeById(id.toString())
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                return EpisodeResult(
                    air_date = "",
                    id = id,
                    characters = defCharacterList,
                    created = "",
                    episode = "",
                    name = "",
                    url = ""
                )
            }
        } catch (e: Exception) {
            return EpisodeResult(
                air_date = "",
                id = id,
                characters = defCharacterList,
                created = "",
                episode = "",
                name = "",
                url = ""
            )
        }
    }

    override suspend fun getCharactersByIds(ids: String): List<CharacterResult> {
        try {
            val respons = episodeApi.getCharactersByIds(ids)
            if (respons.isSuccessful) {
                return respons.body()!!
            } else {
                return defCharacterResultList
            }
        } catch (e: java.lang.Exception) {
            return defCharacterResultList
        }
    }

    companion object {
        val defEpisodeList = emptyList<String>()
        val defCharacterList = emptyList<String>()
        val defEpisodeResultList = emptyList<EpisodeResult>()
        val defCharacterResultList = emptyList<CharacterResult>()
        val defLocatio = CharacterLocation("", "")
        val defOrigin = CharacterOrigin("", "")
    }
}