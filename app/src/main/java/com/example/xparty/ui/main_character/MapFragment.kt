package com.example.xparty.ui.main_character
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.withStateAtLeast
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.databinding.FragmentMapBinding
import com.example.xparty.utlis.LocationProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var map: MapView
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var startPoint: GeoPoint
    private lateinit var mapController: IMapController
    private val viewModel: LocationProvider by viewModels()
    private var locationBtnClicked:Boolean =false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val dataList = arguments?.get("ListsItems")
        Configuration.getInstance().userAgentValue = context?.packageName
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        startPoint = GeoPoint(0.0, 0.0)
        mapController = map.controller
        map.setMultiTouchControls(true)
        binding.myLocationMapFragment.setOnClickListener {
            Toast.makeText(this.requireContext(), "Loading location", Toast.LENGTH_SHORT).show()
             viewModel.result.observe(viewLifecycleOwner) { location ->
                if (location != null) {
                    startPoint.longitude=location.longitude
                    startPoint.latitude=location.latitude
                    binding.tvLon.text = startPoint.longitude.toString()
                    binding.tvLat.text = startPoint.latitude.toString()
                    putMarker(startPoint)
                    mapController.setZoom(19.0);
                    mapController.setCenter(startPoint)
                }
            }
        }

        binding.listViewMapFragment.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_mapFragment_to_partiesListFragment, arguments)
        }
        return binding.root
    }



    private fun putMarker(geoPoint: GeoPoint) {
        val marker = Marker(map)
        marker.position = geoPoint
        marker.icon= ContextCompat.getDrawable(this.requireContext(),R.drawable.marker)
        marker.title ="Your postion here"
        marker.showInfoWindow()
        map.overlays.add(marker)

    }




}
