package com.example.petcare.ui.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private lateinit var userRepository : UserRepository

    private val _registerInfo: MutableLiveData<AppConstants.Companion.RegisterErrors> = MutableLiveData()
    val registerInfo: LiveData<AppConstants.Companion.RegisterErrors> get() = _registerInfo

    fun initRepositories(context: Context) {
        userRepository = UserRepository(context)
    }

    fun registerUser(user: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.flowUserByEmail(user.email).onEach {
                if (it != null) {
                    _registerInfo.postValue(AppConstants.Companion.RegisterErrors.ALREADY_EXIST)
                } else {
                    userRepository.addUser(user)
                    _registerInfo.postValue(AppConstants.Companion.RegisterErrors.SUCCESS)
                }
            }.collect()
        }
    }
}