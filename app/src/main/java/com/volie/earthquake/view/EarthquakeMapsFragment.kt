package com.volie.earthquake.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.volie.earthquake.R
import com.volie.earthquake.databinding.FragmentEarthquakeMapsBinding
import com.volie.earthquake.model.EarthquakeModel
import com.volie.earthquake.viewmodel.EarthquakeMapsViewModel

class EarthquakeMapsFragment : Fragment() {
    private var _mBinding: FragmentEarthquakeMapsBinding? = null
    private val mBinding get() = _mBinding!!
    private lateinit var mViewModel: EarthquakeMapsViewModel
    lateinit var args: EarthquakeModel


    private val callback = OnMapReadyCallback { googleMap ->
        setLocationInfo(googleMap)
    }

    private fun setLocationInfo(googleMap: GoogleMap) {
        val earthquake = LatLng(args.lat, args.lng)
        googleMap.addMarker(
            MarkerOptions().position(earthquake)
                .title(args.name)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(earthquake, 10f))
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = EarthquakeMapsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentEarthquakeMapsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            args = EarthquakeMapsFragmentArgs.fromBundle(it).earthquakeParcelable
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setDetailInfo()
    }

    private fun setDetailInfo() {
        with(mBinding.include) {
            txtName.text = args.name
            txtDate.text = args.date
            txtTime.text = args.time
            txtMag.text = args.magnitudeText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}