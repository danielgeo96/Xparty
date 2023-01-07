package com.example.xparty.ui.party_character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xparty.R
import com.example.xparty.adapters.EventsAdapter
import com.example.xparty.data.Party
import com.example.xparty.databinding.AddPartyLayoutBinding
import com.example.xparty.databinding.FragmentMyEventsBinding
import com.example.xparty.ui.MyEventsViewModel
import kotlinx.coroutines.launch

class MyEventsFragment : Fragment() {

    private var _binding : FragmentMyEventsBinding? = null
    private val binding get() = _binding!!
//    private val eventsList : ArrayList<Party> = ArrayList()
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var linearLayoutManager: LinearLayoutManager
//    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        container?.removeAllViews()
        _binding = FragmentMyEventsBinding.inflate(inflater, container, false)

//        recyclerView = _binding?.myEventsRecycler!!
//        linearLayoutManager = LinearLayoutManager(this.context)
//        recyclerView.layoutManager = linearLayoutManager
//        recyclerView.itemAnimator = DefaultItemAnimator()
//        eventsAdapter = EventsAdapter(ArrayList())
//        recyclerView.adapter = eventsAdapter
//
//        val viewModel : MyEventsViewModel by viewModels()
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.
//            }
//        }
        return binding.root
    }

}