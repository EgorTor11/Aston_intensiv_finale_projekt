package com.example.aston_intensiv_finale_projekt.presentation.episode.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.app.App
import com.example.aston_intensiv_finale_projekt.databinding.FragmentEpisodeDetailBinding
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.CharacterDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class EpisodeDetailFragment : BaseFragment<FragmentEpisodeDetailBinding>() {
    @Inject
    lateinit var episodeDetailViewModelFactory: EpisodeDetailViewModelFactory
    lateinit var viewModel: EpisodeDetailViewModel
    override fun getViewBinding(): FragmentEpisodeDetailBinding {
        return FragmentEpisodeDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).isItemSelectedListenerBlock = true
        (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.episodes

        viewModel = ViewModelProvider(this, episodeDetailViewModelFactory).get(
            EpisodeDetailViewModel::class.java
        )

        val adapter = EpisodeDetailAdapter(object : EpisodeDetailAdapter.Listener {

            override fun onRootClick(characterResult: CharacterResultUI) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    CharacterDetailFragment.getInstance(characterResult.image, characterResult.id)
                ).addToBackStack(null).commit()
            }


        })
        (binding.rcEpisodeDetail.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
        binding.rcEpisodeDetail.adapter = adapter

        viewModel.getEpisodeById(requireArguments().getInt(ID_KEY))
        viewModel.episodeResultLiveData.observe(viewLifecycleOwner) {
            if (it.name == "") {
                binding.tvNameEpisodeDetail.text = requireArguments().getString(NAME_KEY)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_detali_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                with(binding) {
                    tvNameEpisodeDetail.text = it.name
                    tvAirDateDetail.text = it.air_date
                    tvEpisodeCodeDetail.text = it.episode
                    var idsEpisodeQuery = ""
                    it.characters.forEach { link ->
                        idsEpisodeQuery = idsEpisodeQuery + "," + link.substringAfter(
                            CHARACTER_DELIMITER
                        )
                    }
                    viewModel.characterResultListLiveData.observe(viewLifecycleOwner) { list ->
                        adapter.submitList(list)
                    }
                    viewModel.getCharactersListByIds(idsEpisodeQuery)
                }
            }
        }
    }

    companion object {
        fun getInstance(name: String, id: Int): EpisodeDetailFragment {
            return EpisodeDetailFragment().apply {
                arguments = bundleOf(NAME_KEY to name, ID_KEY to id)
            }
        }

        const val NAME_KEY = "name"
        const val ID_KEY = "id"
        const val CHARACTER_DELIMITER = "character/"
    }
}