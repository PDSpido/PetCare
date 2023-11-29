package com.example.petcare.ui.donation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentDonationConfirmationBinding
import com.example.petcare.util.AppConstants

class DonationConfirmationFragment : Fragment() {

    private val viewModel: DonationViewModel by viewModels()

    private lateinit var binding: FragmentDonationConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationConfirmationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            donationConfirmationButton.setOnClickListener {
                if (donationConfirmationTittleInput.text.toString().isEmpty()) {
                    showErrorToast()
                } else {
                    with(donationConfirmationTittleInput.text.toString().toFloat()) {
                        if (this <= 0) {
                            showErrorToast()
                        } else {
                            Log.i("pedro", "confirm $this")
                            viewModel.addContribution(
                                arguments?.getString(AppConstants.POST_ID_TO_CONFIRMATION)!!,
                                arguments?.getFloat(AppConstants.POST_VALUE_TO_CONFIRMATION)!! + this

                            )
                            findNavController().navigate(R.id.donationListFragment)
                        }
                    }
                }
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            "Tentativa de fazer doação com valor invalido, doação rejeitada",
            Toast.LENGTH_SHORT
        ).show()
    }
}