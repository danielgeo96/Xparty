package com.example.xparty.ui.user_character
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xparty.databinding.MapLayoutBinding
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

class mapFragment:Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView
    private var _binding: MapLayoutBinding? =null
    private  val  binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MapLayoutBinding.inflate(inflater,container,false)
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        map = binding.map
        return map.rootView
    }


}