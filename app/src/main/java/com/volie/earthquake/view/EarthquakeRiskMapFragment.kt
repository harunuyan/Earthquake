package com.volie.earthquake.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.volie.earthquake.R
import com.volie.earthquake.databinding.FragmentEarthquakeRiskMapBinding

class EarthquakeRiskMapFragment : Fragment() {

    private var _mBinding: FragmentEarthquakeRiskMapBinding? = null
    private val mBinding get() = _mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentEarthquakeRiskMapBinding.inflate(inflater, container, false)
        mBinding.imgRiskMap.load("https://www.afad.gov.tr/kurumlar/afad.gov.tr/24212/pics/image-b592cc237f473.png") {
            crossfade(true)
            placeholder(R.drawable.img_risk_map)
        }
        return mBinding.root
    }
}