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

class FeedFragment : Fragment(), View.OnClickListener {

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.getAllPosts()

        binding.tietFeedSearchBar.doOnTextChanged { text, _, _, _ ->
            viewModel.getAllPostsFromText("%$text%")
        }
    }

    private fun setListeners() {
        viewModel.postListData.observe(viewLifecycleOwner) {
            binding.feedPostRecyclerView.adapter =
                FeedAdapter(it, this)
        }
    }

    override fun onClick(v: View) {
        Toast.makeText(requireContext(), "Post reportado", Toast.LENGTH_SHORT).show()
    }

}