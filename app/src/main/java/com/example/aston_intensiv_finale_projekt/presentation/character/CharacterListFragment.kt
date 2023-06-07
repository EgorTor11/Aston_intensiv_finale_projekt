package com.example.aston_intensiv_finale_projekt.presentation.character

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.aston_intensiv_finale_projekt.R
import com.example.aston_intensiv_finale_projekt.app.App
import com.example.aston_intensiv_finale_projekt.databinding.FragmentCharacterListBinding
import com.example.aston_intensiv_finale_projekt.databinding.FragmentFilterCharacterBinding
import com.example.aston_intensiv_finale_projekt.presentation.LoadAdapter
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.detail.CharacterDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterFilter
import com.example.aston_intensiv_finale_projekt.presentation.character.models.CharacterResultUI
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>() {
    @Inject
    lateinit var characterViewModelFactory: CharacterViewModelFactory
    lateinit var viewModel: CharacterViewModel
    override fun getViewBinding() = FragmentCharacterListBinding.inflate(layoutInflater)
    lateinit var filterBinding: FragmentFilterCharacterBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.hide()
        if ((requireActivity() as MainActivity).isItemSelectedListenerBlock) {
            (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.characters
        }
        filterBinding = FragmentFilterCharacterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, characterViewModelFactory).get(
            CharacterViewModel::class.java
        )
        val adapter = CharacterAdapter(object : CharacterAdapter.Listener {

            override fun onRootClick(characterResult: CharacterResultUI) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        CharacterDetailFragment.getInstance(
                            characterResult.image,
                            characterResult.id
                        )
                    )
                    .addToBackStack(null).commit()
            }

        })
        (binding.recyclerViewCharacter.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
        binding.recyclerViewCharacter.adapter =
            adapter.withLoadStateHeaderAndFooter(header = LoadAdapter(), footer = LoadAdapter())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFullListCharacter().collectLatest {
                adapter.submitData(it)
            }
        }
        binding.searchEditText.addTextChangedListener {
            viewModel.setSearchByFilter(CharacterFilter(it.toString()))
        }
        adapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.recyclerViewCharacter.isVisible = refreshState != LoadState.Loading
            binding.progressBar.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                    refreshState.error.localizedMessage ?: getString(R.string.default_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = refreshState is LoadState.Loading
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
        binding.btnFilter.setOnClickListener {
            initBottomFilter()
        }
    }

    private fun getFilterResultCharacter(): CharacterFilter {
        var selectedStatus = ""
        var selectedChipId = filterBinding.statusChooseChip.checkedChipId
        if (selectedChipId != -1) {
            val selectedChipStatus: Chip =
                filterBinding.statusChooseChip.findViewById(selectedChipId)
            selectedStatus = selectedChipStatus.text.toString()
        }

        var selectedGender = ""
        selectedChipId = filterBinding.genderChooseChip.checkedChipId
        if (selectedChipId != -1) {
            val selectedChipGender: Chip =
                filterBinding.genderChooseChip.findViewById(selectedChipId)
            selectedGender = selectedChipGender.text.toString()
        }

        return CharacterFilter(
            filterBinding.searchByNameTextInputLayoutCharacter.editText?.text.toString(),
            filterBinding.searchBySpeciesTextInputLayoutCharacter.editText?.text.toString(),
            selectedStatus,
            selectedGender
        )
    }

    private fun initBottomFilter() = with((filterBinding)) {
        val dialog = BottomSheetDialog(requireContext())
        if (filterBinding.root.parent != null) {
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)

        confirmBtn.setOnClickListener {
            viewModel.setSearchByFilter(getFilterResultCharacter())
        }
        dialog.show()
    }
}
