package com.example.xparty.ui.main_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.xparty.R
import com.example.xparty.databinding.FragementPartySearchBinding

class PartySearchFragment : Fragment(){
    private var _binding:FragementPartySearchBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragementPartySearchBinding.inflate(inflater,container,false)

        binding.floatingActionButton.setOnClickListener {
        view?.findNavController()?.navigate(R.id.action_mainFragmentStart_to_mapFragment)
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