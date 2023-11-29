package com.example.petcare.ui.createPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.petcare.R
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.databinding.FragmentCreatePostBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreatePostFragment : Fragment() {

    private val viewModel: CreatePostViewModel by viewModels()

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(layoutInflater)
        binding.createPostType.onItemSelectedListener = SpinnerListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSpinner()
        with(binding) {
            createPostButton.setOnClickListener {
                if (verifyFields()) {
                    viewModel.createPost(
                        PostEntity(
                            postUid = "",
                            postType = getPostType(createPostType.selectedView as TextView).toLong(),
                            picture = "https://www.mobly.com.br/blog/wp-content/uploads/2019/10/lar-pet-friendly-4.jpg",
                            userId = Firebase.auth.currentUser?.uid,
                            userType = UserRepository().getUserType(Firebase.auth.currentUser?.uid),
                            description = createPostDescriptionInput.text.toString(),
                            title = createPostTittleInput.text.toString(),
                            valueDesired = if (createPostAmountInput.text.toString().isEmpty()) 0f
                            else createPostAmountInput.text.toString().toFloat(),
                            valueDonated = 0f
                        )
                    )
                    Toast.makeText(requireContext(), "Post realizado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun verifyFields(): Boolean {
        var isAllFilled = true
        with(binding) {
            if (createPostTittleInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostTittleInputLayout)
            } else setToCorrect(createPostTittleInputLayout)

            if (createPostSpeciesInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostSpeciesInputLayout)
            } else setToCorrect(createPostSpeciesInputLayout)

            if (createPostRaceInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostRaceInputLayout)
            } else setToCorrect(createPostRaceInputLayout)

            if (createPostAgeInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostAgeInputLayout)
            } else setToCorrect(createPostAgeInputLayout)

            if (createPostSexInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostSexInputLayout)
            } else setToCorrect(createPostSexInputLayout)

            if (createPostColorInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostColorInputLayout)
            } else setToCorrect(createPostColorInputLayout)

            if (createPostDescriptionInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(createPostDescriptionInputLayout)
            } else setToCorrect(createPostDescriptionInputLayout)

            if (getPostType(createPostType.selectedView as TextView) == 2) {
                if (createPostAmountInput.text.isNullOrEmpty()) {
                    isAllFilled = false
                    setToError(createPostAmountInputLayout)
                } else setToCorrect(createPostAmountInputLayout)
            }
        }
        return isAllFilled
    }

    private fun setToError(textInputLayout: TextInputLayout) {
        textInputLayout.error = "Campo obrigatorio"
    }

    private fun setToCorrect(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
    }

    private fun configureSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.post_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.createPostType.adapter = adapter
        }
    }

    private fun getPostType(v: TextView): Int =
        when (v.text) {
            "Alerta" -> 0
            "Doação" -> 1
            else -> 0
        }

    inner class SpinnerListener : OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            if (position == 0) {
                binding.createPostAmountInputLayout.visibility = View.GONE
            } else {
                binding.createPostAmountInputLayout.visibility = View.VISIBLE
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }
}