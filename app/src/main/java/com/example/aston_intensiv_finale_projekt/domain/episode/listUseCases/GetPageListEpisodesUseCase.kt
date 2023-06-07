package com.example.aston_intensiv_finale_projekt.domain.episode.listUseCases

import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPageListEpisodesUseCase @Inject constructor(val episodeRepository: EpisodeRepositoryInterface) {
    suspend fun execute(name: String, episode: String): Flow<PagingData<EpisodeResultUI>> {
        return  episodeRepository.getEpisodesFromApi(name, episode)
    }
}