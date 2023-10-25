package com.example.petcare.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            registerButton.setOnClickListener {
                if (verifyFields()) {
                    viewModel.registerUser(
                        UserEntity(
                            uid = 0,
                            username = registerNameInput.text.toString(),
                            document = registerDocumentInput.text.toString(),
                            email = registerEmailInput.text.toString(),
                            contact = registerPhoneInput.text.toString(),
                            password = registerPasswordInput.text.toString(),
                            userType = 1
                        )
                    )
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }

    private fun verifyFields(): Boolean {
        var isAllFilled = true
        with(binding) {
            if (registerNameInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(registerNameInputLayout)
            } else setToCorrect(registerNameInputLayout)

            if (registerDocumentInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(registerDocumentInputLayout)
            } else setToCorrect(registerDocumentInputLayout)

            if (registerEmailInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(registerEmailInputLayout)
            } else setToCorrect(registerEmailInputLayout)

            if (registerPhoneInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(registerPhoneInputLayout)
            } else setToCorrect(registerPhoneInputLayout)

            if (registerPasswordInput.text.isNullOrEmpty()) {
                isAllFilled = false
                setToError(registerPasswordInputLayout)
            } else setToCorrect(registerPasswordInputLayout)

            if (!registerPrivacyRadioButton.isChecked) {
                registerPrivacyRadioButton.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.standard_error
                    )
                )
                isAllFilled = false
            } else registerPrivacyRadioButton.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.standard_text_color)
            )


        }
        return isAllFilled
    }

    private fun setToError(textInputLayout: TextInputLayout) {
        textInputLayout.error = "Campo obrigatorio"
    }

    private fun setToCorrect(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
    }

}