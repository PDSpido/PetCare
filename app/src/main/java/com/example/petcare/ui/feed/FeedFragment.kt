package com.example.petcare.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.petcare.databinding.FragmentFeedBinding
import com.example.petcare.util.AppConstants

class FeedFragment : Fragment(), View.OnClickListener {

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var binding: FragmentFeedBinding

    private var activeType: Long = AppConstants.Companion.UserType.COMMON.ordinal.toLong()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPosts(activeType)
        with(binding) {
            tietFeedSearchBar.doOnTextChanged { text, _, _, _ ->
                if (text != null) {
                    if (text.isNotBlank()) viewModel.getAllPostsFromText(text)
                    else viewModel.getAllPosts(activeType)
                }
            }
            feedOngTabButton.setOnClickListener {
                activeType = AppConstants.Companion.UserType.ONG.ordinal.toLong()
                viewModel.getAllPosts(activeType)
            }
            feedPopularTabButton.setOnClickListener {
                activeType = AppConstants.Companion.UserType.COMMON.ordinal.toLong()
                viewModel.getAllPosts(activeType)
            }
        }
        setListeners()
    }

    private fun setListeners() {
        viewModel.postListData.observe(viewLifecycleOwner) {
            binding.feedPostRecyclerView.adapter =
                FeedAdapter(it, this)
            if (it.isEmpty()) binding.nothingToSeeFeed.nothingToSeeText.visibility = View.VISIBLE
            else binding.nothingToSeeFeed.nothingToSeeText.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        Toast.makeText(requireContext(), "Post reportado", Toast.LENGTH_SHORT).show()
    }

}