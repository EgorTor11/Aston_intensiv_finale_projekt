package com.example.aston_intensiv_finale_projekt.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.domain.character.listUseCases.GetPagingListCharactersUseCase
import javax.inject.Inject

class CharacterViewModelFactory @Inject constructor(val getPagingListCharactersUseCase: GetPagingListCharactersUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterViewModel(getPagingListCharactersUseCase) as T
    }
}