package com.example.xparty.ui.main_character

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.databinding.FragementPartySearchBinding
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PartySearchFragment : Fragment() {
    private var binding: FragementPartySearchBinding by autoCleared()
    private val viewModel: PartySearchFragmentViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        binding = FragementPartySearchBinding.inflate(inflater, container, false)
        sharedPreferences = context?.getSharedPreferences("pref",Context.MODE_PRIVATE)
        binding.floatingActionButton.setOnClickListener {

            if(sharedPreferences?.getBoolean("isLogin",false)!!) {

                viewModel.events.observe(viewLifecycleOwner) {
                    when (it.status) {
                        is Loading -> binding.partySearchProgressBar.isVisible = true
                        is Success -> {
                            if (!it.status.data.isNullOrEmpty()) {
                                binding.partySearchProgressBar.isVisible = false

                                val bundle = bundleOf("ListsItems" to it.status.data)

                                view?.findNavController()
                                    ?.navigate(R.id.action_mainFragmentStart_to_mapFragment, bundle)
                            }
                        }
                        is com.example.xparty.utlis.Error -> {
                            binding.partySearchProgressBar.isVisible = false
                            Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }else{
                Toast.makeText(
                    requireContext(),
                    "You must be logged in",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}