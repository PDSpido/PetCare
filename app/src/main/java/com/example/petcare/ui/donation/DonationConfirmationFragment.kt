package com.example.petcare.ui.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            donationConfirmationButton.setOnClickListener {

                viewModel.addContribution(
                    arguments?.getInt(AppConstants.POST_ID_TO_CONFIRMATION)!!,
                    donationConfirmationTittleInput.text.toString().toFloat()
                )

                findNavController().navigate(R.id.donationListFragment)
            }
            viewModel.getAllPosts()
        }
    }
}