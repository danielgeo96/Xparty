package com.example.xparty.ui.main_character
import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.xparty.databinding.FragementPartySearchBinding
import com.example.xparty.ui.user_character.CurrentLocationViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.xparty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartySearchFragment : Fragment() {
    private var _binding: FragementPartySearchBinding? = null
    private val binding get() = _binding!!
    private  val currentLocationViewModel: CurrentLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = FragementPartySearchBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            //TODO: fix crash when click after login.
            view?.findNavController()?.navigate(R.id.action_SearchFragment_to_mapFragment)

//          view?.findNavController()?.navigate(R.id.action_mainFragmentStart_to_mapFragment)

//            Toast.makeText(view?.context,"Worked!",Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}