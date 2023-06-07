package com.example.aston_intensiv_finale_projekt.domain.location.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.domain.location.LocationRepositoryInterface
import javax.inject.Inject

class GetCharactersListByIdsUseCase@Inject constructor(val locationRepository: LocationRepositoryInterface) {
    suspend fun execute(ids: String): List<CharacterResult> {
        return  locationRepository.getCharactersByIds(ids)
    }
}