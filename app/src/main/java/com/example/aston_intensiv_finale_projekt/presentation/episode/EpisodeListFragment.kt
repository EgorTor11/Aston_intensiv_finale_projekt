package com.example.aston_intensiv_finale_projekt.presentation.episode

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
import com.example.aston_intensiv_finale_projekt.databinding.FragmentEpisodeListBinding
import com.example.aston_intensiv_finale_projekt.databinding.FragmentFilterEpisodeBinding
import com.example.aston_intensiv_finale_projekt.presentation.LoadAdapter
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.detail.EpisodeDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeFilter
import com.example.aston_intensiv_finale_projekt.presentation.episode.models.EpisodeResultUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class EpisodeListFragment : BaseFragment<FragmentEpisodeListBinding>() {
    @Inject
    lateinit var episodeViewModelFactory: EpisodeViewModelFactory
    lateinit var viewModel: EpisodeViewModel
    override fun getViewBinding() = FragmentEpisodeListBinding.inflate(layoutInflater)
    lateinit var filterBinding: FragmentFilterEpisodeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.hide()
        if ((requireActivity() as MainActivity).isItemSelectedListenerBlock) {
            (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.episodes
        }
        filterBinding = FragmentFilterEpisodeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, episodeViewModelFactory).get(
            EpisodeViewModel::class.java
        )
        val adapter = EpisodeAdapter(object : EpisodeAdapter.Listener {
            override fun onRootClick(episodeResult: EpisodeResultUI) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        EpisodeDetailFragment.getInstance(episodeResult.name, episodeResult.id)
                    )
                    .addToBackStack(null).commit()
            }

        })
        (binding.rcEpisode.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.rcEpisode.adapter =
            adapter.withLoadStateHeaderAndFooter(header = LoadAdapter(), footer = LoadAdapter())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFullListEpisodes().collectLatest {
                adapter.submitData(it)
            }
        }


        binding.searchEditTextEpisode.addTextChangedListener {
            viewModel.setSearchByFilter(EpisodeFilter(it.toString()))
        }
        adapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.rcEpisode.isVisible = refreshState != LoadState.Loading
            binding.progressBarEpisode.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                        refreshState.error.localizedMessage ?: getString(R.string.default_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            if (state.append is LoadState.Error) {
                Snackbar.make(
                    binding.root, "не возможно ",
                    //     refreshState.error.localizedMessage ?: "ERROR!!!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (binding.swipeRefreshLayoutEpisode.isRefreshing) {
                binding.swipeRefreshLayoutEpisode.isRefreshing = refreshState is LoadState.Loading
            }
        }
        binding.swipeRefreshLayoutEpisode.setOnRefreshListener {
            //  binding.progressBar.visibility = View.INVISIBLE
            adapter.refresh()
        }
        binding.btnFilterEpisode.setOnClickListener {
            initBottomFilter()
        }

    }

    private fun getFilterResultEpisode(): EpisodeFilter {
        return EpisodeFilter(
            filterBinding.searchByNameEditText.editText?.text.toString(),
            filterBinding.searchByEpisodeEditText.editText?.text.toString()
        )
    }

    private fun initBottomFilter() = with((filterBinding)) {
        val dialog = BottomSheetDialog(requireContext())
        if (filterBinding.root.parent != null) {
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)

        confirmBtn.setOnClickListener {
            viewModel.setSearchByFilter(getFilterResultEpisode())
        }
        dialog.show()
    }

    private fun showBottomNav() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
    }
}

