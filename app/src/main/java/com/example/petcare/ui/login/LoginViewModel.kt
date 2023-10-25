package com.example.petcare.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var userRepository : UserRepository

    private val _loginInfo: MutableLiveData<Boolean> = MutableLiveData()
    val loginInfo: LiveData<Boolean> get() = _loginInfo

    fun initRepositories(context: Context) {
        userRepository = UserRepository(context)
    }

    fun tryLogin(email: String, password: String) {
        viewModelScope.launch {
            userRepository.flowUserByEmail(email).onEach {
                Log.i("pedro", "$it")
                if (it != null) {
                    if (it.password == password){
                        _loginInfo.postValue(true)
                        setLogin()
                    }
                    else  _loginInfo.postValue(false)
                } else  _loginInfo.postValue(false)
            }.collect()
        }
    }

    private fun setLogin() {
        //TODO
    }
}