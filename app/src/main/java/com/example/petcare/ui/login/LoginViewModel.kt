package com.example.petcare.ui.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository
    private lateinit var sharedPreferences: SharedPreferences

    private val _loginInfo: MutableLiveData<Boolean> = MutableLiveData()
    val loginInfo: LiveData<Boolean> get() = _loginInfo

    fun initRepositories(context: Context) {
        userRepository = UserRepository(context)
        sharedPreferences =
            context.getSharedPreferences(AppConstants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun tryLogin(email: String, password: String) {
        viewModelScope.launch {
            userRepository.flowUserByEmail(email).onEach {
                if (it != null) {
                    if (it.password == password) {
                        _loginInfo.postValue(true)
                        setLogin(it.uid, it.userType)
                    } else _loginInfo.postValue(false)
                } else _loginInfo.postValue(false)
            }.collect()
        }
    }

    private fun setLogin(uid: Int, userType: Int) {
        sharedPreferences.edit().putInt(AppConstants.LOGIN_SHARED_PREFERENCES, uid).apply()
        sharedPreferences.edit().putInt(AppConstants.LOGIN_TYPE_SHARED_PREFERENCES, userType).apply()

    }
}