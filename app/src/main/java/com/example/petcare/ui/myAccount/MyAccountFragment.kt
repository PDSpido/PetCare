package com.example.petcare.ui.myAccount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petcare.R
import com.example.petcare.databinding.FragmentMyAccountBinding
import com.example.petcare.ui.MainActivity
import com.example.petcare.util.AppConstants

class MyAccountFragment : Fragment() {

    private val viewModel: MyAccountViewModel by viewModels()

    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.postListData

        viewModel.getAllPostsByUser(
            requireContext().getSharedPreferences(
                AppConstants.APP_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            ).getInt(AppConstants.LOGIN_SHARED_PREFERENCES, 0)
        )

        viewModel.getUserById(
            requireContext().getSharedPreferences(
                AppConstants.APP_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            ).getInt(AppConstants.LOGIN_SHARED_PREFERENCES, 0)
        )

        binding.tvExitHome.setOnClickListener { onExitClicked() }
    }

    private fun setListeners() {
        viewModel.postListData.observe(viewLifecycleOwner) {
            binding.rvMyAccountPostList.adapter =
                MyAccountAdapter(it)
        }

        viewModel.userData.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://plus.unsplash.com/premium_photo-1680700148924-4abdd12c89b5")
                    .into(ivMyAccountPhoto)
                tvMyAccountUsername.text = it.username
                tvMyAccountPhoneNumber.text = it.contact
            }
        }
    }

    private fun onExitClicked() {
        requireContext().getSharedPreferences(
            AppConstants.APP_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit().clear().apply()
        (activity as MainActivity).setNaviBarToInvisible()
        findNavController().navigate(R.id.loginFragment)
    }
}