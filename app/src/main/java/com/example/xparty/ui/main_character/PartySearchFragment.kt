package com.example.xparty.ui.main_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.data.api_eventbrite.ApiViewModel
import com.example.xparty.databinding.FragementPartySearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PartySearchFragment : Fragment() {
    private var _binding: FragementPartySearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        _binding = FragementPartySearchBinding.inflate(inflater, container, false)
        binding.floatingActionButton.setOnClickListener {
            //TODO: fix crash when click after login.
            view?.findNavController()?.navigate(R.id.action_mainFragmentStart_to_mapFragment)
            viewModel.user.value
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