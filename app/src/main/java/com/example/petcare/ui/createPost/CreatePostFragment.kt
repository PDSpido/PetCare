package com.example.petcare.ui.createPost

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.databinding.FragmentCreatePostBinding
import com.example.petcare.util.AppConstants

class CreatePostFragment : Fragment() {

    private val viewModel: CreatePostViewModel by viewModels()

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createPostButton.setOnClickListener {
                viewModel.createPost(
                    PostEntity(
                        uid = 0,
                        postType = 1,
                        picture = "https://plus.unsplash.com/premium_photo-1680700148924-4abdd12c89b5",
                        userId = requireContext().getSharedPreferences(
                            AppConstants.APP_SHARED_PREFERENCES,
                            Context.MODE_PRIVATE
                        ).getInt(AppConstants.LOGIN_SHARED_PREFERENCES, 0),
                        description = createPostDescriptionInput.text.toString(),
                        tittle = createPostTittleInput.text.toString(),
                        valueDesired = createPostAmountInput.text.toString().toFloat(),
                        valueDonated = 0f
                    )
                )

                findNavController().navigate(R.id.donationListFragment)
            }
        }
    }

}