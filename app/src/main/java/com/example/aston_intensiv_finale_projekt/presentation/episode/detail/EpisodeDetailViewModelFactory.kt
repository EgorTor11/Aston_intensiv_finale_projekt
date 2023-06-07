package com.example.aston_intensiv_finale_projekt.presentation.episode.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.data.CharacterRepository
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases.GetCharactersByIdsUseCase
import com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases.GetEpisodeByIdUseCase
import javax.inject.Inject

class EpisodeDetailViewModelFactory @Inject constructor(
    val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    val getCharactersByIdsUseCase: GetCharactersByIdsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeDetailViewModel(getEpisodeByIdUseCase, getCharactersByIdsUseCase) as T
    }
}