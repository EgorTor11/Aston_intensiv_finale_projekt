package com.example.aston_intensiv_finale_projekt.data.paging.episode

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.aston_intensiv_finale_projekt.data.mappers.EpisodeMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.EpisodeApi
import com.example.aston_intensiv_finale_projekt.data.room.episode.EpisodeDao
import com.example.aston_intensiv_finale_projekt.data.room.episode.episodeEntity.EpisodeResultDb

@OptIn(ExperimentalPagingApi::class)
class EpisodeRemoteMediator(
    private val episodeDao: EpisodeDao,
    private val episodeApi: EpisodeApi,
    private val name: String,
    private val episode: String,
) : RemoteMediator<Int, EpisodeResultDb>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeResultDb>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val episodes = fetchEpisodes(limit, offset)
            if (loadType == LoadType.REFRESH) {
                Log.d("ref", "refresh")
                episodeDao.refreshEpisodes(episodes)
            } else {
                episodeDao.insertEpisodes(episodes)
            }
            MediatorResult.Success(
                endOfPaginationReached = episodes.size < limit
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

    private suspend fun fetchEpisodes(
        limit: Int,
        offset: Int
    ): List<EpisodeResultDb> {
        return episodeApi.getEpisode(pageIndex, name,episode).results.map {
            EpisodeMapper().mapEpisodeResultForEpisodeResultDb(
                it
            )
        }
    }
}