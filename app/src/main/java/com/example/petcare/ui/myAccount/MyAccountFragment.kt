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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyAccountFragment : Fragment() {

    private val viewModel: MyAccountViewModel by viewModels()

    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.postListData

        Firebase.auth.currentUser?.let {
            viewModel.getAllPostsByUser(it.uid)
            viewModel.getUserById(it.uid)
        }

        binding.tvExitHome.setOnClickListener {
            Firebase.auth.signOut()
            onExitClicked()
        }
    }

    private fun setListeners() {
        viewModel.postListData.observe(viewLifecycleOwner) {
            binding.rvMyAccountPostList.adapter =
                MyAccountAdapter(it)
            if (it.isEmpty()) binding.nothingToSeeMyAccount.nothingToSeeText.visibility = View.VISIBLE
            else binding.nothingToSeeMyAccount.nothingToSeeText.visibility = View.GONE
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