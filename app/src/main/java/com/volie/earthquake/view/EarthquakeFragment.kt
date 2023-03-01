package com.volie.earthquake.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.volie.earthquake.R
import com.volie.earthquake.databinding.FragmentEarthquakeBinding
import com.volie.earthquake.view.adapter.EarthquakeAdapter
import com.volie.earthquake.viewmodel.EarthquakeViewModel

class EarthquakeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _mBinding: FragmentEarthquakeBinding? = null
    private val mBinding get() = _mBinding!!

    private val mAdapter = EarthquakeAdapter()
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

        mBinding.recyclerView.adapter = mAdapter
        mViewModel.getEarthquakes() //TODO
        initObserver()

        mBinding.swipeRefreshLayout.setOnRefreshListener {
            with(mBinding) {
                recyclerView.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
            }
            mViewModel.refreshEarthquakes()
            initObserver()
        }


        // Search menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                // Search
                val search = menu.findItem(R.id.search_item)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@EarthquakeFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // Press enter
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughtDatabase(query)
        }
        return true
    }

    // letter by
    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughtDatabase(query)
        }
        return true
    }

    private fun searchThroughtDatabase(query: String) {
        val searchQuery = "%$query%"
        mViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) {
            it?.let {
                mAdapter.setItems(it)
            }
        }
    }

    fun initObserver() {
        mBinding.recyclerView.visibility = View.VISIBLE
        mViewModel.earthquakes.observe(viewLifecycleOwner) {
            mAdapter.setItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }
}