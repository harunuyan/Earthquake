package com.volie.earthquake.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.volie.earthquake.databinding.FragmentEarthquakeBinding

class EarthquakeFragment : Fragment() {

    private var _mBinding: FragmentEarthquakeBinding? = null
    private val mBinding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentEarthquakeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}