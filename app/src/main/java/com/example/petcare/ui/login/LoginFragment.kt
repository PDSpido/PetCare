package com.example.petcare.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.petcare.R
import com.example.petcare.data.PetCareDatabase
import com.example.petcare.databinding.FragmentLoginBinding
import com.example.petcare.ui.MainActivity

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        activity = requireActivity() as MainActivity

        viewModel.initRepositories(requireContext())

        viewModel.retrieveAllUserData()
    }

}