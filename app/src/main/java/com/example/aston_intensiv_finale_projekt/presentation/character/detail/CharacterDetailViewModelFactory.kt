package com.example.aston_intensiv_finale_projekt.presentation.character.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.data.CharacterRepository
import com.example.aston_intensiv_finale_projekt.domain.character.CharacterRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetCharacterByIdUseCase
import com.example.aston_intensiv_finale_projekt.domain.character.detailUseCases.GetEpisodesListByIdsUseCase
import javax.inject.Inject

class CharacterDetailViewModelFactory @Inject constructor(
    val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    val getEpisodesListByIdsUseCase: GetEpisodesListByIdsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(getCharacterByIdUseCase, getEpisodesListByIdsUseCase) as T
    }
}