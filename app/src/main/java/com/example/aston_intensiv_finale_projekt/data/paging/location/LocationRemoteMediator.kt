package com.example.aston_intensiv_finale_projekt.data.paging.location

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.aston_intensiv_finale_projekt.data.mappers.EpisodeMapper
import com.example.aston_intensiv_finale_projekt.data.mappers.LocationMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.location.LocationApi
import com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity.EpisodeResultDb
import com.example.aston_intensiv_finale_projekt.data.room.location.LocationDao
import com.example.aston_intensiv_finale_projekt.data.room.location.locationEntity.LocationResultDb

@OptIn(ExperimentalPagingApi::class)
class LocationRemoteMediator(
    private val locationDao: LocationDao,
    private val locationApi: LocationApi,
    private val name: String,
    private val type: String,
    private val dimension: String
) : RemoteMediator<Int, LocationResultDb>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationResultDb>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val locations = fetchLocations(limit, offset)
            if (loadType == LoadType.REFRESH) {
                Log.d("ref", "refresh")
                locationDao.refreshLocations(locations)
            } else {
                locationDao.insertLocations(locations)
            }
            MediatorResult.Success(
                endOfPaginationReached = locations.size < limit
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

    private suspend fun fetchLocations(
        limit: Int,
        offset: Int
    ): List<LocationResultDb> {
        return locationApi.getLocation(pageIndex, name, type, dimension).results.map {
            LocationMapper().mapLocationResultForLocationResultDb(
                it
            )
        }
    }
}