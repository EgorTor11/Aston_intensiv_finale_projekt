package com.example.aston_intensiv_finale_projekt.data.paging.character

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.CharacterApi
import com.example.aston_intensiv_finale_projekt.data.room.character.CharacterDao
import com.example.aston_intensiv_finale_projekt.data.room.character.characterEntity.CharacterResultDb
import retrofit2.http.Query

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi,
    private val name: String,
    private val status: String,
    private val species: String,
    private val gender: String,
) : RemoteMediator<Int, CharacterResultDb>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterResultDb>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val characters = fetchCharacters(limit, offset)
            if (loadType == LoadType.REFRESH) {
                Log.d("ref", "refresh")
                characterDao.refreshCharacters(characters)
            } else {
                characterDao.insertCharacters(characters)
            }
            MediatorResult.Success(
                endOfPaginationReached = characters.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    private suspend fun fetchCharacters(
        limit: Int,
        offset: Int
    ): List<CharacterResultDb> {
        return characterApi.getCharacter(pageIndex,name,status,species,gender).results.map {
            CharacterMapper().mapCharacterResultForCharacterResultDb(
                it
            )
        }
    }
}