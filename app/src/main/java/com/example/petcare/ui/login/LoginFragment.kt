package com.example.petcare.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petcare.R
import com.example.petcare.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel.initRepositories(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        with(binding) {
            loginNewAccount.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }

            loginAccessButton.setOnClickListener {
                viewModel.tryLogin(
                    email = loginEmailInput.text.toString(),
                    password = loginPasswordInput.text.toString()
                )
            }
        }

    }

    private fun setListeners() {
        viewModel.loginInfo.observe(viewLifecycleOwner) {
            if (it) Toast.makeText(requireContext(), "Entrou", Toast.LENGTH_LONG).show()
            else Toast.makeText(requireContext(), "Falhou", Toast.LENGTH_LONG).show()
        }
    }

}