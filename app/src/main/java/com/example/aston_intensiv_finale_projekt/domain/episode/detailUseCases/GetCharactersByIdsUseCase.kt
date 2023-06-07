package com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import javax.inject.Inject

class GetCharactersByIdsUseCase @Inject constructor(val episodeRepository: EpisodeRepositoryInterface) {
    suspend fun execute(ids: String): List<CharacterResult> {
        return  episodeRepository.getCharactersByIds(ids)
    }
}