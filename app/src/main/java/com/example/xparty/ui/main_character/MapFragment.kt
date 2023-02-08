package com.example.xparty.ui.main_character

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.data.models.Party
import com.example.xparty.databinding.FragmentMapBinding
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.LocationProvider
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var map: MapView
    private var binding: FragmentMapBinding by autoCleared()
    private lateinit var startPoint: GeoPoint
    private lateinit var mapController: IMapController
    private val viewModel: MapFragmentViewModel by viewModels()
    private val locationProvider: LocationProvider by viewModels()
    private var locationBtnClicked: Boolean = false
    private var dataList: ArrayList<Party> = ArrayList()
    private var selfLocationIndex: Int = -1
    private lateinit var selfMarker: Marker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        Configuration.getInstance().userAgentValue = context?.packageName
        map = binding.map
        selfMarker = Marker(map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController = map.controller
        map.setMultiTouchControls(true)

        viewModel.eventsStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.mapSecondProgressBar.isVisible = true
                    binding.map.isVisible = false
                }
                is Success -> {
                    if (!it.status.data.isNullOrEmpty()) {
                        binding.mapSecondProgressBar.isVisible = false
                        for (event in it.status.data){
                            if(!dataList.contains(event)){
                                dataList.add(event)
                            }
                            val geoPoint = GeoPoint(event.latitude, event.longitude)
                            putEventMarker(geoPoint, event.partyName)
                        }
                        binding.map.isVisible = true
                    }
                }
                is com.example.xparty.utlis.Error -> {
                    binding.mapSecondProgressBar.isVisible = false
                    binding.map.isVisible = false

                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.events.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.mapFirstProgressBar.isVisible = true
                    binding.map.isVisible = false
                }
                is Success -> {
                    if (!it.status.data.isNullOrEmpty()) {
                        binding.mapFirstProgressBar.isVisible = false
                        for (event in it.status.data){
                            if(!dataList.contains(event)){
                                dataList.add(event)
                            }
                            val geoPoint = GeoPoint(event.latitude, event.longitude)
                            putEventMarker(geoPoint, event.partyName)
                        }
                        binding.map.isVisible = true
                    }
                }
                is com.example.xparty.utlis.Error -> {
                    binding.mapFirstProgressBar.isVisible = false
                    binding.map.isVisible = false

                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }


        if (arguments?.get("latitude") == null) {
            setLocation(
                locationProvider.result.value?.latitude,
                locationProvider.result.value?.longitude
            )
        } else {
            setLocation(
                arguments?.get("latitude") as Double?,
                arguments?.get("longitude") as Double
            )
        }

        locationProvider.result.observe(viewLifecycleOwner) { location ->
            putSelfMarker(location)
        }

        binding.myLocationMapFragment.setOnClickListener{
            Toast.makeText(this.requireContext(), "Loading location", Toast.LENGTH_SHORT).show()
            setLocation(
                locationProvider.result.value?.latitude,
                locationProvider.result.value?.longitude
            )
        }

        binding.listViewMapFragment.setOnClickListener {
            val bundle = Bundle(bundleOf("dataList" to dataList))
            selfLocationIndex = -1
            view?.findNavController()
                ?.navigate(R.id.action_mapFragment_to_partiesListFragment, bundle)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun putSelfMarker(location: Location?) {
        if (location != null) {
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            selfMarker.position = geoPoint
            selfMarker.icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.marker)
            map.overlays.add(selfMarker)


            selfLocationIndex = if (selfLocationIndex == -1) {
                map.overlays.add(selfMarker)
                map.overlays.lastIndex
            } else {
                try {
                    map.overlays.removeAt(selfLocationIndex)
                    map.overlays.add(selfMarker)
                    map.overlays.lastIndex
                } catch (e: Exception) {
                    Log.e("TAG", e.toString())
                }
            }
        }
    }

    private fun putEventMarker(geoPoint: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = geoPoint
        marker.icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.marker)
        marker.title = title
        marker.showInfoWindow()
        map.overlays.add(marker)
    }

    private fun setLocation(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            binding.tvLon.text = longitude.toString()
            binding.tvLat.text = latitude.toString()
            mapController.setZoom(19.0);
            val geoPoint = GeoPoint(latitude, longitude)
            mapController.setCenter(geoPoint)
        }
    }

}
