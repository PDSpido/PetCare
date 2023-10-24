package com.example.petcare.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var userRepository : UserRepository

    fun initRepositories(context: Context) {
        userRepository = UserRepository(context)
    }

    fun setRawData() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.addUser(
                UserEntity(
                    1,
                    email = "pedro.henriquevieira@outlook.com",
                    password = "teste123",
                    contact = "19994263597",
                    document = "test",
                    username = "Username",
                    userType = 1)
            )
        }
    }

    fun retrieveAllUserData() {
        viewModelScope.launch {
            userRepository.flowAllUser().collect {
                Log.i("pedro", "$it")
            }
        }
    }
}