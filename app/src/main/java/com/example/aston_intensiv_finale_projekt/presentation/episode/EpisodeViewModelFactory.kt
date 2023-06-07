package com.example.aston_intensiv_finale_projekt.presentation.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.episode.listUseCases.GetPageListEpisodesUseCase
import javax.inject.Inject

class EpisodeViewModelFactory @Inject constructor(val getPageListEpisodesUseCase: GetPageListEpisodesUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(getPageListEpisodesUseCase) as T
    }
}