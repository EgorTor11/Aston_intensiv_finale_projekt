package com.example.aston_intensiv_finale_projekt.domain.character.listUseCases

import androidx.paging.PagingData
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingListCharactersUseCase @Inject constructor (val characterRepository: CharacterRepositoryInterface) {
    suspend fun execute(name: String, status: String,
                        species: String, gender: String): Flow<PagingData<CharacterResultUI>> {
        return characterRepository.getCharactersFromApi(name, status, species, gender)
    }
}