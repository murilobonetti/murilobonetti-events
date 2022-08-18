package com.murilobonetti.events.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.murilobonetti.events.R
import com.murilobonetti.events.checkin.ModalCheckinFragment
import com.murilobonetti.events.databinding.FragmentDetailsBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val event = DetailFragmentArgs.fromBundle(requireArguments()).selectedEvent
        val viewModelFactory = DetailViewModelFactory(event)

        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailViewModel::class.java)

        binding.checkinButton.setOnClickListener {
            val modalCheckin = ModalCheckinFragment.newInstance(event.id)
            modalCheckin.show(requireActivity().supportFragmentManager, "modalCheckin")
        }

        binding.directionsButton.setOnClickListener {
            openMaps(getMapIntent())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)

        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareEvent() {
        startActivity(getShareIntent())
    }

    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(
                    R.string.share_event_message,
                    binding.viewModel!!.selectedEvent.value?.title
                )
            )

        return shareIntent
    }

    private fun openMaps(mapIntent: Intent) {
        mapIntent.resolveActivity(requireActivity().packageManager)?.let {
            startActivity(mapIntent)
        }
    }

    private fun getMapIntent(): Intent {
        val latitude = binding.viewModel?.selectedEvent?.value?.latitude
        val longitude = binding.viewModel?.selectedEvent?.value?.longitude
        val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        return mapIntent
    }

}