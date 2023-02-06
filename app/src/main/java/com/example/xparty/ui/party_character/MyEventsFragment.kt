package com.example.xparty.ui.party_character

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xparty.R
import com.example.xparty.adapters.EventsAdapter
import com.example.xparty.data.models.Party
import com.example.xparty.data.repository.EventsRepository
import com.example.xparty.data.repository.firebase.EventsRepositoryFirebase
import com.example.xparty.databinding.FragmentMyEventsBinding
import com.example.xparty.ui.MainActivity
import com.example.xparty.utlis.Loading
import com.example.xparty.utlis.Success
import com.example.xparty.utlis.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication


@AndroidEntryPoint
class MyEventsFragment : Fragment() {

    private var binding: FragmentMyEventsBinding by autoCleared()
    private val viewModel: MyEventsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        binding = FragmentMyEventsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myEventsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.myEventsRecycler.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        binding.myEventsRecycler.adapter = EventsAdapter(object : EventsAdapter.EventListener{
            override fun onEventClicked(event: Party) {
            }

            override fun onEventLongClicked(event: Party) {
                viewModel.deleteEvent(event.id)
            }

            override fun onImgClick(event: Party) {
                TODO("Not yet implemented")
            }
        })

        viewModel.eventsStatus.observe(viewLifecycleOwner){
            when(it.status){
                is Loading ->{
                    binding.myEventsProgressBar.isVisible = true
                }

                is Success -> {
                    binding.myEventsProgressBar.isVisible = false

                    (binding.myEventsRecycler.adapter as EventsAdapter).setEvents(it.status.data!!)
                }

                is com.example.xparty.utlis.Error -> {
                    binding.myEventsProgressBar.isVisible = false
                }
            }
        }

        viewModel.deleteEvent.observe(viewLifecycleOwner){
            when(it.status) {
                is Loading -> {
                    binding.myEventsProgressBar.isVisible = true
                }

                is Success -> {
                    binding.myEventsProgressBar.isVisible = false
                }

                is com.example.xparty.utlis.Error -> {
                    binding.myEventsProgressBar.isVisible = false
                }
            }
        }
    }
}