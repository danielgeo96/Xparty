package com.example.xparty.ui.main_character
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import com.example.xparty.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.views.MapView



@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var map : MapView
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var startPoint:GeoPoint
    private lateinit var mapController:IMapController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        container?.removeAllViews()
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        Configuration.getInstance().userAgentValue = context?.packageName
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController = map.controller
        mapController.setZoom(9.5)
        map.setMultiTouchControls(true)
        startPoint = GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint)
        return binding.root
    }







}