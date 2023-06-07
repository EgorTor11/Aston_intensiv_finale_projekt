package com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases

import com.example.aston_intensiv_finale_projekt.data.retrofit.character.models.CharacterResult
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(val characterRepository: CharacterRepositoryInterface) {
    suspend fun execute(id:Int): CharacterResult {
        return characterRepository.getCharacterById(id)
    }
}