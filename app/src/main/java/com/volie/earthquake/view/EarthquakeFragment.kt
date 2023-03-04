package com.volie.earthquake.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.elevation.SurfaceColors
import com.volie.earthquake.R
import com.volie.earthquake.databinding.FragmentEarthquakeBinding
import com.volie.earthquake.model.EarthquakeModel
import com.volie.earthquake.view.adapter.EarthquakeAdapter
import com.volie.earthquake.viewmodel.EarthquakeViewModel

class EarthquakeFragment : Fragment(), SearchView.OnQueryTextListener, EarthquakeAdapter.Listener {

    private var _mBinding: FragmentEarthquakeBinding? = null
    private val mBinding get() = _mBinding!!
    private val mAdapter = EarthquakeAdapter(this)
    private lateinit var mViewModel: EarthquakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = EarthquakeViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentEarthquakeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupMenu()
        mBinding.recyclerView.adapter = mAdapter
        mViewModel.getEarthquakes()
        swipeRefresh()
        initObserver()
        setStatusAndNavBarColor()
    }

    private fun setupMenu() {
        // Search menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.earthquake_menu, menu)
                // Search
                val search = menu.findItem(R.id.search_item)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@EarthquakeFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> requireActivity()
                        .onBackPressedDispatcher
                        .onBackPressed()
                    R.id.menu_sort_high_mag -> mViewModel.sortHighMag()
                    R.id.menu_sort_low_mag -> mViewModel.sortLowMag()
                    R.id.menu_sort_last_mag -> mViewModel.getEarthquakes()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun swipeRefresh() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            with(mBinding) {
                recyclerView.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
            }
            mViewModel.refreshEarthquakes()
            initObserver()
        }
    }

    private fun setStatusAndNavBarColor() {
        val window = activity?.window
        val color = SurfaceColors.SURFACE_2.getColor(requireContext())
        window!!.statusBarColor = color
        window.navigationBarColor = color
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // Press enter
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        // letter by
        if (query != null) {
            mViewModel.searchDatabase(query)
        }
        return true
    }

    private fun initObserver() {
        mBinding.recyclerView.visibility = View.VISIBLE
        mViewModel.earthquakes.observe(viewLifecycleOwner) {
            mAdapter.setItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onItemClick(earthquakeModel: EarthquakeModel) {
        val action = EarthquakeFragmentDirections.actionEarthquakeFragmentToEarthquakeMapsFragment(
            earthquakeModel
        )
        findNavController().navigate(action)
    }
}