package com.example.aston_intensiv_finale_projekt.presentation.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.episode.listUseCases.GetPageListEpisodesUseCase
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


class EpisodeViewModel(val getPageListEpisodesUseCase: GetPageListEpisodesUseCase) : ViewModel() {
    private val searchByFilter = MutableLiveData(EpisodeFilter(""))

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    suspend fun getFullListEpisodes() = searchByFilter.asFlow()
        .debounce(500)
        .flatMapLatest {
            getPageListEpisodesUseCase.execute(it.name, it.episode)
        }
        .cachedIn(viewModelScope)

    fun setSearchByFilter(episodeFilter: EpisodeFilter) {
        searchByFilter.value = episodeFilter
    }
}

