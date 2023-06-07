package com.example.aston_intensiv_finale_projekt.presentation.character.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.app.App
import com.example.aston_intensiv_finale_projekt.databinding.FragmentCharacterDetailBinding
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.detail.EpisodeDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import com.example.aston_intensiv_finale_projekt.presentation.location.detail.LocationDetailFragment
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {
    @Inject
    lateinit var characterDetailViewModelFactory: CharacterDetailViewModelFactory
    lateinit var viewModel: CharacterDetailViewModel
    override fun getViewBinding(): FragmentCharacterDetailBinding =
        FragmentCharacterDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).isItemSelectedListenerBlock = true
        (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.characters

        viewModel = ViewModelProvider(this, characterDetailViewModelFactory).get(
            CharacterDetailViewModel::class.java
        )
        viewModel.getCharacterById(requireArguments().getInt(ID_KEY))
        val adapter = CharacterDetailAdapter(object : CharacterDetailAdapter.Listener {
            override fun onRootClick(episodeResult: EpisodeResultUI) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    EpisodeDetailFragment.getInstance(episodeResult.name, episodeResult.id)
                ).addToBackStack(null).commit()
            }


        })
        (binding.rcCharacterDetail.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
        binding.rcCharacterDetail.adapter = adapter

        viewModel.characterResultLiveData.observe(viewLifecycleOwner) { characterResult ->
            if (characterResult.name == "") {
                Glide.with(requireContext()).load(requireArguments().getString(IMAGE_KEY))
                    .into(binding.imageAvatarDetail)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_detali_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Glide.with(requireContext()).load(characterResult.image)
                    .into(binding.imageAvatarDetail)
                with(binding) {
                    tvNameDetail.text = characterResult.name
                    genderViewDetail.text = characterResult.gender
                    speciesViewDetail.text = characterResult.species
                    statusViewDetail.text = characterResult.status
                    locationViewDetail.text = characterResult.location.name
                    originViewDetail.text = characterResult.origin.name
                    var idsEpisodeQuery = ""
                    characterResult.episode.forEach { link ->
                        idsEpisodeQuery = idsEpisodeQuery + "," + link.substringAfter(
                            EPISODE_DELIMITER
                        )
                    }
                    viewModel.episodeResultListLiveData.observe(viewLifecycleOwner) { list ->
                        adapter.submitList(list)
                    }
                    viewModel.getEpisodesListByIds(idsEpisodeQuery)
                    locationViewDetail.setOnClickListener {
                        if (characterResult.location.url != "") {
                            parentFragmentManager.beginTransaction().replace(
                                R.id.fragment_container,
                                LocationDetailFragment.getInstance(
                                    characterResult.location.name,
                                    characterResult.location.url.substringAfter(LOCATION_DELIMITER)
                                        .toInt()
                                )
                            ).addToBackStack(null).commit()
                        }
                    }
                    originViewDetail.setOnClickListener {
                        if (characterResult.origin.url != "") {
                            parentFragmentManager.beginTransaction().replace(
                                R.id.fragment_container,
                                LocationDetailFragment.getInstance(
                                    characterResult.origin.name,
                                    characterResult.origin.url.substringAfter(LOCATION_DELIMITER)
                                        .toInt()
                                )
                            ).addToBackStack(null).commit()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun getInstance(image: String, id: Int): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = bundleOf(IMAGE_KEY to image, ID_KEY to id)
            }
        }

        const val IMAGE_KEY = "image"
        const val ID_KEY = "id"
        const val EPISODE_DELIMITER = "episode/"
        const val LOCATION_DELIMITER = "location/"
    }
}