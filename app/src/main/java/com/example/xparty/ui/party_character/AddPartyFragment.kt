package com.example.xparty.ui.party_character

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.xparty.R
import com.example.xparty.data.Party
import com.example.xparty.databinding.AddPartyLayoutBinding
import com.example.xparty.ui.MainActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddPartyFragment : Fragment() {

    private var TAG: String = "AddPartyFragment"
    private var _binding: AddPartyLayoutBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var eventTitle: String
    private lateinit var eventLocation: String
    private lateinit var eventDescription: String
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = AddPartyLayoutBinding.inflate(inflater, container, false)

        sharedPreferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        _binding?.publishBtn?.setOnClickListener {

            if (isValidEvenTitle() && isValidLocation() && isValidDescription()) {
                bindingEventData()
                publish()
                //change fragment
                val mainActivityView = (activity as MainActivity)
                val navController: NavController = Navigation.findNavController(
                    mainActivityView,
                    R.id.nav_host_fragment
                )
                navController.popBackStack()
                navController.navigate(R.id.SearchFragment)
                mainActivityView.replaceFragment("Main Page")
            }

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

    private fun bindingEventData() {
        eventTitle = _binding?.partyNameInput?.text.toString()
        eventLocation = _binding?.partyLocationInput?.text.toString()
        eventDescription = _binding?.partyDescriptionInput?.text.toString()
    }

    private fun isValidEvenTitle(): Boolean {
        return if (_binding?.partyNameInput?.text?.isEmpty() == true) {
            _binding?.partyNameInput?.error = "This field can't be empty"
            false
        } else {
            _binding?.partyNameInput?.error = null
            true
        }
    }

    private fun isValidLocation(): Boolean {
        return if (_binding?.partyLocationInput?.text?.isEmpty() == true) {
            _binding?.partyLocationInput?.error = "This field can't be empty"
            false
        } else {
            _binding?.partyLocationInput?.error = null
            true
        }
    }

    private fun isValidDescription(): Boolean {
        return if (_binding?.partyDescriptionInput?.text?.isEmpty() == true) {
            _binding?.partyDescriptionInput?.error = "This field can't be empty"
            false
        } else {
            _binding?.partyDescriptionInput?.error = null
            true
        }
    }

    private fun publish() {

        var event:Party = Party(partyName = eventTitle, partyLocation = eventLocation, partyDescription = eventDescription , userId = sharedPreferences?.getString("userId",null))

        db = FirebaseFirestore.getInstance()
        db.collection("events").add(event)

    }
}