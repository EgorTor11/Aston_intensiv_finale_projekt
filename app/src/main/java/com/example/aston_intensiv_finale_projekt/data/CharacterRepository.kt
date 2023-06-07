package com.example.aston_intensiv_finale_projekt.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterLocation
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterOrigin
import com.example.aston_intensiv_finale_projekt.data.paging.character.CharacterRemoteMediator
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.CharacterApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.data.room.character.CharacterDao
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    val characterApi: CharacterApi,
    val characterDao: CharacterDao,
    val context: Context
):CharacterRepositoryInterface {
    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersFromApi(
        name: String, status: String,
        species: String, gender: String
    ): Flow<PagingData<CharacterResultUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = true,
                initialLoadSize = 20
            ),
            remoteMediator = CharacterRemoteMediator(
                characterDao,
                characterApi,
                name,
                status,
                species,
                gender
            ),
            pagingSourceFactory = { characterDao.getPagingSource(name, status, species, gender) }
        ).flow.map { it.map { CharacterMapper().mapCharacterResultDbForCharacterResultUI(it) } }
    }

    override suspend fun getCharacterById(id: Int): CharacterResult {
        try {
            val response = characterApi.getCharacterById(id.toString())
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                return CharacterResult(
                    "",
                    defEpisodeList,
                    "",
                    id,
                    "",
                    defLocatio,
                    "",
                    defOrigin,
                    "",
                    "",
                    "",
                    ""
                )
            }
        } catch (e: Exception) {
            return CharacterResult(
                "",
                defEpisodeList,
                "",
                id,
                "",
                defLocatio,
                "",
                defOrigin,
                "",
                "",
                "",
                ""
            )
        }
    }

    override suspend fun getEpisodesByIds(ids: String): List<EpisodeResult> {
        try {
            val respons = characterApi.getEpisodesByIds(ids)
            if (respons.isSuccessful) {
             return respons.body()!!
            } else {
              return defEpisodeResultList
            }
        }catch (e:java.lang.Exception){
            return defEpisodeResultList
        }
    }

    companion object {
        val defEpisodeList = emptyList<String>()
        val defEpisodeResultList= emptyList<EpisodeResult>()
        val defLocatio = CharacterLocation("", "")
        val defOrigin = CharacterOrigin("", "")
    }
}