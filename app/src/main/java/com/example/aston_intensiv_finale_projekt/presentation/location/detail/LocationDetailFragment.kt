package com.example.aston_intensiv_finale_projekt.presentation.location.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.app.App
import com.example.aston_intensiv_finale_projekt.databinding.FragmentLocationDetailBinding
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.CharacterDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class LocationDetailFragment : BaseFragment<FragmentLocationDetailBinding>() {
    @Inject
    lateinit var locationDetailViewModelFactory: LocationDetailViewModelFactory
    lateinit var viewModel: LocationDetailViewModel
    override fun getViewBinding(): FragmentLocationDetailBinding {
        return FragmentLocationDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.show()
        (requireActivity() as MainActivity).isItemSelectedListenerBlock = true
        (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.locations

        viewModel = ViewModelProvider(this, locationDetailViewModelFactory).get(
            LocationDetailViewModel::class.java
        )

        val adapter = LocationDetailAdapter(object : LocationDetailAdapter.Listener {

            override fun onRootClick(characterResult: CharacterResultUI) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    CharacterDetailFragment.getInstance(characterResult.image, characterResult.id)
                ).addToBackStack(null).commit()
            }


        })
        (binding.rcLocationDetail.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
        binding.rcLocationDetail.adapter = adapter

        viewModel.getLocationById(requireArguments().getInt(ID_KEY))
        viewModel.locationResultLiveData.observe(viewLifecycleOwner) {
            if (it.name == "") {
                binding.tvNameLocationDetail.text = requireArguments().getString(NAME_KEY)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_detali_toast),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                with(binding) {
                    tvNameLocationDetail.text = it.name
                    binding.tvTypeDetailLocation.text = it.type
                    binding.tvDimensionDetail.text = it.dimension
                    var idsLocationQuery = ""
                    it.residents.forEach { link ->
                        idsLocationQuery =
                            idsLocationQuery + "," + link.substringAfter(CHARACTER_DELIMITER)
                    }
                    viewModel.characterResultListLiveData.observe(viewLifecycleOwner) { list ->
                        adapter.submitList(list)
                    }
                    viewModel.getCharactersListByIds(idsLocationQuery)
                }
            }
        }
    }

    companion object {
        fun getInstance(name: String, id: Int): LocationDetailFragment {
            return LocationDetailFragment().apply {
                arguments = bundleOf(NAME_KEY to name, ID_KEY to id)
            }
        }
        const val NAME_KEY = "name"
        const val ID_KEY = "id"
        const val CHARACTER_DELIMITER = "character/"
    }
}