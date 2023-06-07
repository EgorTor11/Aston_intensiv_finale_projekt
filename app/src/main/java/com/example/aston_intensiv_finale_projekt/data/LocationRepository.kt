package com.example.aston_intensiv_finale_projekt.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aston_intensiv_finale_projekt.data.mappers.LocationMapper
import com.example.aston_intensiv_finale_projekt.data.paging.location.LocationRemoteMediator
import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.LocationApi
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.models.LocationResult
import com.example.aston_intensiv_finale_projekt.data.room.location.LocationDao
import com.example.aston_intensiv_finale_projekt.domain.location.LocationRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepository(
    val locationApi: LocationApi,
    val lcationDao: LocationDao,
    val context: Context
):LocationRepositoryInterface {
    @OptIn(ExperimentalPagingApi::class)
    override fun getLocationsFromApi(
        name: String, type: String,dimension:String
    ): Flow<PagingData<LocationResultUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = true,
                initialLoadSize = 20
            ),
            remoteMediator = LocationRemoteMediator(
                lcationDao,
                locationApi,
                name,
                type,
                dimension
            ),
            pagingSourceFactory = { lcationDao.getPagingSource(name, type,dimension) }
        ).flow.map { it.map { LocationMapper().mapLocationResultDbForLocationResultUI(it) } }
    }

    override suspend fun getLocationById(id: Int): LocationResult {
        try {
            val response = locationApi.getLocationById(id.toString())
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                return LocationResult(
                    created="" ,
                 dimension="" ,
                id= id,
                name= "",
                residents= emptyList<String>(),
                 type="" ,
                 url=""
                )
            }
        } catch (e: Exception) {
            return LocationResult(
                created="" ,
                dimension="" ,
                id= id,
                name= "",
                residents= emptyList<String>(),
                type="" ,
                url=""
            )
        }
    }

    override suspend fun getCharactersByIds(ids: String): List<CharacterResult> {
        try {
            val respons = locationApi.getCharactersByIds(ids)
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
        val defCharacterResultList = emptyList<CharacterResult>()
    }
}