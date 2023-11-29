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
import com.example.petcare.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Firebase.auth.currentUser != null) goToMain()

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
            if (it) goToMain()
            else Toast.makeText(requireContext(), "Falhou", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMain() {
        (activity as MainActivity).setNaviBarToVisible()
        findNavController().navigate(R.id.feedFragment)
    }

}