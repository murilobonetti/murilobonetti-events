package com.murilobonetti.events.eventslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.murilobonetti.events.databinding.FragmentEventsBinding
import com.murilobonetti.events.eventslist.EventsAdapter.OnClickListener


class EventsFragment : Fragment() {

    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this)[EventsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.eventsList.adapter = EventsAdapter(OnClickListener { event ->
            viewModel.displayEventDetails(event)
        })

        viewModel.navigateToEventDetails.observe(viewLifecycleOwner) { event ->
            event?.let {
                findNavController().navigate(
                    EventsFragmentDirections.actionEventsFragmentToDetailFragment(event)
                )
                viewModel.displayEventDetailsComplete()
            }
        }


        return binding.root
    }

}