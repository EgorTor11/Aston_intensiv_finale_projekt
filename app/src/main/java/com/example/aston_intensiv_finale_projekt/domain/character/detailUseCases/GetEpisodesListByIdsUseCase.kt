package com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import javax.inject.Inject

class GetEpisodesListByIdsUseCase @Inject constructor(val characterRepository: CharacterRepositoryInterface) {
    suspend fun execute(ids: String): List<EpisodeResult> {
        return characterRepository.getEpisodesByIds(ids)
    }
}