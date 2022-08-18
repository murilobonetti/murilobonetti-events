package com.murilobonetti.events.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.murilobonetti.events.R
import com.murilobonetti.events.data.parameters.CheckinParameters
import com.murilobonetti.events.databinding.FragmentModalCheckinBinding
import com.murilobonetti.events.network.ApiStatus
import com.murilobonetti.events.utils.getTextString
import com.murilobonetti.events.utils.showToast
import com.murilobonetti.events.utils.validateEmail

private const val EVENT_ID = "eventId"

class ModalCheckinFragment : BottomSheetDialogFragment() {

    private var eventId: String? = null

    private var _binding: FragmentModalCheckinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CheckinViewModel by lazy {
        ViewModelProvider(this)[CheckinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getString(EVENT_ID)
        }

        if (eventId == null) {
            Toast.makeText(requireContext(), "Algo deu errado", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModalCheckinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCheckin.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }

            val checkinParameters = CheckinParameters(
                eventId!!,
                binding.name.text.toString(),
                binding.email.text.toString()
            )

            viewModel.doCheckin(checkinParameters)
        }

        viewModel.dismissModal.observe(viewLifecycleOwner) {
            if (it) {
                dismiss()
                viewModel.dismissModalComplete()
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                handleResult(it)
                viewModel.handleResultComplete()
            }
        }
    }

    private fun handleResult(result: ApiStatus) {
        if (result == ApiStatus.ERROR) {
            showToast(requireContext(), R.string.check_in_failed)
        }

        if (result == ApiStatus.DONE) {
            showToast(requireContext(), R.string.check_in_successful)
        }
    }

    private fun validateFields(): Boolean {
        if (binding.name.getTextString().isNullOrEmpty()) {
            binding.name.error = getString(R.string.mandatory_name_field)
            return false
        }

        if (binding.email.text.isNullOrEmpty()) {
            binding.email.error = getString(R.string.mandatory_email_field)
            return false
        }

        if (!binding.email.validateEmail()) {
            binding.email.error = getString(R.string.invalid_email)
            return false
        }

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance(eventId: String) =
            ModalCheckinFragment().apply {
                arguments = Bundle().apply {
                    putString(EVENT_ID, eventId)
                }
            }
    }
}