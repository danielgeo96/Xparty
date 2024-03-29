package com.example.xparty.ui.main_character

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.databinding.FragementPartySearchBinding
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PartySearchFragment : Fragment() {
    private var binding: FragementPartySearchBinding by autoCleared()
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.removeAllViews()
        binding = FragementPartySearchBinding.inflate(inflater, container, false)
        sharedPreferences = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        binding.floatingActionButton.setOnClickListener {

            if (sharedPreferences?.getBoolean("isLogin", false)!!) {

                view?.findNavController()
                    ?.navigate(R.id.action_mainFragmentStart_to_mapFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "You must be logged in",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }

}