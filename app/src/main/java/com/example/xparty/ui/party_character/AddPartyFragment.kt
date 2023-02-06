package com.example.xparty.ui.party_character

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.firebase.EventsRepositoryFirebase
import com.example.xparty.databinding.AddPartyLayoutBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPartyFragment : Fragment() {

    private var TAG: String = "AddPartyFragment"
    private var binding: AddPartyLayoutBinding by autoCleared()
    private lateinit var eventTitle: String
    private lateinit var eventLocation: String
    private lateinit var eventDescription: String
    private val viewModel : AddPartyViewModel by viewModels()
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var img = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        binding = AddPartyLayoutBinding.inflate(inflater, container, false)

        binding.publishBtn.setOnClickListener {

            if (isValidEvenTitle() && isValidLocation() && isValidDescription()) {
                bindingEventData()
                viewModel.addEvent(eventTitle,eventDescription,longitude,latitude,false,img)
            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addEventStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.publishBtn.isEnabled = false
                    binding.addEventProgressbar.isVisible = true
                }

                is Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Public event - successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val mainActivityView = (activity as MainActivity)
                    val navController: NavController = Navigation.findNavController(
                        mainActivityView,
                        R.id.nav_host_fragment
                    )
                    navController.popBackStack()
                    navController.navigate(R.id.SearchFragment)
                    mainActivityView.replaceFragment("Main Page")
                }

                is com.example.xparty.utlis.Error -> {
                    binding.publishBtn.isEnabled = true
                    binding.addEventProgressbar.isVisible = false
                }
            }
        }
    }

    private fun bindingEventData() {
        eventTitle = binding.partyNameInput.text.toString()
        eventLocation = binding.partyLocationInput.text.toString()
        eventDescription = binding.partyDescriptionInput.text.toString()
    }

    private fun isValidEvenTitle(): Boolean {
        return if (binding.partyNameInput.text?.isEmpty() == true) {
            binding.partyNameInput.error = "This field can't be empty"
            false
        } else {
            binding.partyNameInput.error = null
            true
        }
    }

    private fun isValidLocation(): Boolean {
        return if (binding.partyLocationInput.text?.isEmpty() == true) {
            binding.partyLocationInput.error = "This field can't be empty"
            false
        } else {
            binding.partyLocationInput.error = null
            true
        }
    }

    private fun isValidDescription(): Boolean {
        return if (binding.partyDescriptionInput.text?.isEmpty() == true) {
            binding.partyDescriptionInput.error = "This field can't be empty"
            false
        } else {
            binding.partyDescriptionInput.error = null
            true
        }
    }

}