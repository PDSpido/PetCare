package com.example.petcare.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var userRepository = UserRepository()

    private val _loginInfo: MutableLiveData<Boolean> = MutableLiveData()
    val loginInfo: LiveData<Boolean> get() = _loginInfo

    fun tryLogin(email: String, password: String) {
        viewModelScope.launch {
            _loginInfo.postValue(userRepository.tryLogin(email, password))
        }
    }
}