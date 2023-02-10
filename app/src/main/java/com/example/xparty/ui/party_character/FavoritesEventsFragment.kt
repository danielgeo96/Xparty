package com.example.xparty.ui.party_character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xparty.R
import com.example.xparty.adapters.EventsAdapter
import com.example.xparty.data.models.Party
import com.example.xparty.databinding.AddPartyLayoutBinding
import com.example.xparty.databinding.FragmentFavoritesEventsBinding
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesEventsFragment : Fragment() {

    var binding : FragmentFavoritesEventsBinding by autoCleared()
    private val viewModel: FavoritesEventsFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        binding = FragmentFavoritesEventsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favEventsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.favEventsRecycler.addItemDecoration(
            DividerItemDecoration(context,
                LinearLayoutManager.VERTICAL)
        )
        binding.favEventsRecycler.adapter = EventsAdapter(object : EventsAdapter.EventListener{
            override fun onEventClicked(event: Party) {
            }

            override fun onEventLongClicked(event: Party) {
            }

            override fun onImgClick(event: Party) {
                viewModel.removeFavEvent(event)
            }
        })

        viewModel.eventsStatus.observe(viewLifecycleOwner){
            when(it.status){
                is Loading ->{
                    binding.favEventsProgressBar.isVisible = true
                }

                is Success -> {
                    binding.favEventsProgressBar.isVisible = false
                    (binding.favEventsRecycler.adapter as EventsAdapter).setEvents(it.status.data!!)
                }

                is com.example.xparty.utlis.Error -> {
                    binding.favEventsProgressBar.isVisible = false
                }
            }
        }
    }
}