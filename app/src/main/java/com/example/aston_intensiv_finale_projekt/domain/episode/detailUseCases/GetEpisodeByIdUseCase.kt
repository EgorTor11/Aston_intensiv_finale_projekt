package com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import javax.inject.Inject

class GetEpisodeByIdUseCase @Inject constructor(val episodeRepository: EpisodeRepositoryInterface) {
    suspend fun execute(id: Int): EpisodeResult {
        return  episodeRepository.getEpisodeById(id)
    }
}