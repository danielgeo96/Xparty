package com.example.xparty.ui.main_character
import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.xparty.databinding.FragementPartySearchBinding
import com.example.xparty.ui.user_character.CurrentLocationViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.xparty.R


class PartySearchFragment : Fragment() {
    private var _binding: FragementPartySearchBinding? = null
    private val binding get() = _binding!!


    private val appPerms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET
    )
    private  val currentLocationViewModel: CurrentLocationViewModel by viewModels()

    private val locationRequestLauncher: ActivityResultLauncher<Array<String>>
            =registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
            if(allPermissionsGranted(permissions))
            {
                val navController = NavHostFragment.findNavController(this)
                navController.navigate(R.id.action_mainFragmentStart_to_mapFragment)
                Toast.makeText(requireContext(),"No premssion",Toast.LENGTH_SHORT)

            }
        else{

                Toast.makeText(requireContext(),"No premssion",Toast.LENGTH_SHORT)

            }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = FragementPartySearchBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            //TODO: fix crash when click after login.
//            view?.findNavController()?.navigate(R.id.action_mainFragmentStart_to_mapFragment)
            locationRequestLauncher.launch(appPerms)

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

    private fun allPermissionsGranted(permissions: Map<String,Boolean> ): Boolean {
        for (permission in permissions.values)
        {
            if(!permission)
            {
                return  false
            }
        }
        return true
    }


    private fun getLocationUpdates()
    {
        currentLocationViewModel.location.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(),"Hey",Toast.LENGTH_LONG).show()
        }
    }

}