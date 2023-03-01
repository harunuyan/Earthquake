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
import com.volie.earthquake.viewmodel.EarthquakeMapsViewModel

class EarthquakeMapsFragment : Fragment() {
    private var _mBinding: FragmentEarthquakeMapsBinding? = null
    private val mBinding get() = _mBinding!!

    private lateinit var mViewModel: EarthquakeMapsViewModel
    private var uuid = 0
    private var lat = 0.0
    private var lng = 0.0
    private var name = ""

    private val callback = OnMapReadyCallback { googleMap ->

        val earthquake = LatLng(lat, lng)
        googleMap.addMarker(
            MarkerOptions().position(earthquake)
                .title("$name")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(earthquake, 10f))
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = EarthquakeMapsViewModel(requireContext())
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

        observeLiveData()

        arguments?.let {
            uuid = EarthquakeMapsFragmentArgs.fromBundle(it).uuid
        }

        mViewModel.getEarthquakesMaps(uuid)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun observeLiveData() {
        mViewModel.earthquakeMapsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                with(mBinding) {
                    txtName.text = it.name
                    txtDate.text = it.date
                    txtTime.text = it.time
                    txtMag.text = it.magnitudeText
                }
                name = it.name
                lat = it.lat!!
                lng = it.lng!!
            }
        }
    }
}