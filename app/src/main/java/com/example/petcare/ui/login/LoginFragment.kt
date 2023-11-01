package com.example.petcare.ui.login

import android.content.Context
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
import com.example.petcare.util.AppConstants

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

        if (requireContext().getSharedPreferences(
                AppConstants.APP_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            ).getInt(AppConstants.LOGIN_SHARED_PREFERENCES, 0) != 0
        ) goToMain()


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