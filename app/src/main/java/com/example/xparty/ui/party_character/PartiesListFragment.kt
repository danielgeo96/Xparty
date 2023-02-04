package com.example.xparty.ui.party_character

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xparty.R
import com.example.xparty.adapters.EventsAdapter
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.AllEventsRepository
import com.example.xparty.data.repository.FavoritesEventsRepository
import com.example.xparty.data.repository.firebase.EventsRepositoryFirebase
import com.example.xparty.databinding.AddPartyLayoutBinding
import com.example.xparty.databinding.FragmentPartiesListBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartiesListFragment : Fragment() {

    private var binding: FragmentPartiesListBinding by autoCleared()
    private val viewModel : PartiesListFragmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartiesListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataList : List<Party> = arguments?.get("ListsItems") as List<Party>

        binding.allEventsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.allEventsRecycler.addItemDecoration(
            DividerItemDecoration(context,
                LinearLayoutManager.VERTICAL)
        )
        binding.allEventsRecycler.adapter = EventsAdapter(object : EventsAdapter.EventListener{
            override fun onEventClicked(event: Party) {
            }

            override fun onEventLongClicked(event: Party) {
            }

            override fun onImgClick(event: Party) {
                viewModel.setEvent(event)
            }
        })
        (binding.allEventsRecycler.adapter as EventsAdapter).setEvents(dataList)

    }
}