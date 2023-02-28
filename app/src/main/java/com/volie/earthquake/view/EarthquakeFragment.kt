package com.volie.earthquake.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.volie.earthquake.databinding.FragmentEarthquakeBinding
import com.volie.earthquake.view.adapter.EarthquakeAdapter
import com.volie.earthquake.viewmodel.EarthquakeViewModel

class EarthquakeFragment : Fragment() {

    private var _mBinding: FragmentEarthquakeBinding? = null
    private val mBinding get() = _mBinding!!

    private val mAdapter = EarthquakeAdapter()
    private val mViewModel: EarthquakeViewModel by viewModels()

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
        mViewModel.getEarthquakesFromRemote()
        initObserver()

        mBinding.swipeRefreshLayout.setOnRefreshListener {
            with(mBinding) {
                recyclerView.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
            }
            mViewModel.getEarthquakesFromRemote()
            initObserver()
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