package com.example.aston_intensiv_finale_projekt.presentation.episode.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_finale_projekt.data.EpisodeRepository
import com.example.aston_intensiv_finale_projekt.data.mappers.CharacterMapper
import com.example.aston_intensiv_finale_projekt.data.mappers.EpisodeMapper
import com.example.aston_intensiv_finale_projekt.data.retrofit.episode.models.EpisodeResult
import com.example.aston_intensiv_finale_projekt.domain.episode.EpisodeRepositoryInterface
import com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases.GetCharactersByIdsUseCase
import com.example.aston_intensiv_finale_projekt.domain.episode.detailUseCases.GetEpisodeByIdUseCase
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.example.aston_intensiv_finale_projekt.presentation.episode.detail.detailModel.EpisodeResultDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeDetailViewModel(
    val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    val getCharactersByIdsUseCase: GetCharactersByIdsUseCase
) : ViewModel() {
    val episodeResultLiveData = MutableLiveData<EpisodeResultDetail>()
    val characterResultListLiveData = MutableLiveData<List<CharacterResultUI>>()
    fun getEpisodeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            episodeResultLiveData.postValue(
                EpisodeMapper().mapEpisodeResultForEpisodeResultDetail(
                    getEpisodeByIdUseCase.execute(id)
                )
            )
        }
    }

    fun getCharactersListByIds(ids: String) {
        viewModelScope.launch(Dispatchers.IO) {
            characterResultListLiveData.postValue(
                getCharactersByIdsUseCase.execute(ids)
                    .map { CharacterMapper().mapCharacterResultForCharacterResultUI(it) })
        }
    }
}