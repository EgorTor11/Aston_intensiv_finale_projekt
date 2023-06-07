package com.example.aston_intensiv_finale_projekt.di


import com.example.aston_intensiv_finale_projekt.presentation.character.CharacterListFragment
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.CharacterDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.EpisodeListFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.detail.EpisodeDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.location.LocationListFragment
import com.example.aston_intensiv_finale_projekt.presentation.location.detail.LocationDetailFragment
import dagger.Component

@Component(modules = [AppModule::class,DataModule::class,DomainModule::class])
interface AppComponent{
    fun inject(mainActivity: MainActivity)
    fun inject(characterListFragment: CharacterListFragment)
    fun inject(characterDetailFragment: CharacterDetailFragment)
    fun inject(episodeListFragment: EpisodeListFragment)
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
    fun inject(locationListFragment: LocationListFragment)
    fun inject(locationListFragment: LocationDetailFragment)
}