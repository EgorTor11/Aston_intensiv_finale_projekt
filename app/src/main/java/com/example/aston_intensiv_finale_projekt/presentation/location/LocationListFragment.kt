package com.example.aston_intensiv_finale_projekt.presentation.location

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
import com.example.aston_intensiv_finale_projekt.databinding.FragmentFilterLocationBinding
import com.example.aston_intensiv_finale_projekt.databinding.FragmentLocationListBinding
import com.example.aston_intensiv_finale_projekt.presentation.LoadAdapter
import com.example.aston_intensiv_finale_projekt.presentation.MainActivity
import com.example.aston_intensiv_finale_projekt.presentation.base_fragment.BaseFragment
import com.example.aston_intensiv_finale_projekt.presentation.location.detail.LocationDetailFragment
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationFilter
import com.example.aston_intensiv_finale_projekt.presentation.location.models.LocationResultUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class LocationListFragment : BaseFragment<FragmentLocationListBinding>() {
    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    lateinit var viewModel: LocationViewModel
    override fun getViewBinding() = FragmentLocationListBinding.inflate(layoutInflater)

    lateinit var filterBinding: FragmentFilterLocationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        (requireActivity() as MainActivity).supportActionBar?.hide()
        if ((requireActivity() as MainActivity).isItemSelectedListenerBlock) {
            (requireActivity() as MainActivity).binding.navView.selectedItemId = R.id.locations
        }
        filterBinding = FragmentFilterLocationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, locationViewModelFactory).get(
            LocationViewModel::class.java
        )
        val adapter = LocationAdapter(object : LocationAdapter.Listener {

            override fun onRootClick(locationResult: LocationResultUI) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        LocationDetailFragment.getInstance(locationResult.name, locationResult.id)
                    )
                    .addToBackStack(null).commit()
            }

        })
        (binding.rcLocation.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.rcLocation.adapter =
            adapter.withLoadStateHeaderAndFooter(header = LoadAdapter(), footer = LoadAdapter())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFullListLocations().collectLatest {
                adapter.submitData(it)
            }
        }


        binding.searchEditTextLocation.addTextChangedListener {
            viewModel.setSearchByFilter(LocationFilter(it.toString()))
        }
        adapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.rcLocation.isVisible = refreshState != LoadState.Loading
            binding.progressBarLocation.isVisible = refreshState == LoadState.Loading
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
            if (binding.swipeRefreshLayoutLocation.isRefreshing) {
                binding.swipeRefreshLayoutLocation.isRefreshing = refreshState is LoadState.Loading
            }
        }
        binding.swipeRefreshLayoutLocation.setOnRefreshListener {
            //  binding.progressBar.visibility = View.INVISIBLE
            adapter.refresh()
        }
        binding.btnFilterLocation.setOnClickListener {
            initBottomFilter()
        }

    }

    private fun getFilterResultLocation(): LocationFilter {
        return LocationFilter(
            filterBinding.searchByNameEditTextLocation.editText?.text.toString(),
            filterBinding.searchByTypeEditTextLocation.editText?.text.toString(),
            filterBinding.searchByDimensionEditTextLocation.editText?.text.toString(),
        )
    }

    private fun initBottomFilter() = with((filterBinding)) {
        val dialog = BottomSheetDialog(requireContext())
        if (filterBinding.root.parent != null) {
            (filterBinding.root.parent as ViewGroup).removeView(filterBinding.root)
        }
        dialog.setContentView(filterBinding.root)

        confirmBtnLocation.setOnClickListener {
            viewModel.setSearchByFilter(getFilterResultLocation())
        }
        dialog.show()
    }

    private fun showBottomNav() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
    }
}

