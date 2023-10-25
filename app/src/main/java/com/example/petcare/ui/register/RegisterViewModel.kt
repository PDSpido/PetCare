package com.example.petcare.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private lateinit var userRepository : UserRepository

    fun initRepositories(context: Context) {
        userRepository = UserRepository(context)
    }

    fun registerUser(user: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.addUser(user)
        }
    }
}