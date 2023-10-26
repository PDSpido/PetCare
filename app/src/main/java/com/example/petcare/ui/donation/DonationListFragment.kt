package com.example.petcare.ui.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentDonationListBinding
import com.example.petcare.util.AppConstants

class DonationListFragment : Fragment(), View.OnClickListener {

    private val viewModel: DonationViewModel by viewModels()

    private lateinit var binding: FragmentDonationListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationListBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        with(binding) {
            donationListAddButton.setOnClickListener {
                findNavController().navigate(R.id.createPostFragment)
            }
            viewModel.getAllPosts()
        }
    }

    private fun setListeners() {
        viewModel.postListData.observe(viewLifecycleOwner) {
            binding.donationListRecyclerView.adapter =
                DonationListAdapter(requireContext(), it, this)
        }
    }

    override fun onClick(v: View) {
        findNavController().navigate(R.id.donationConfirmationFragment, Bundle().apply {
            putInt(AppConstants.POST_ID_TO_CONFIRMATION, v.tag as Int)
        })
    }

}