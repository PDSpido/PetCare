package com.example.petcare.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.util.AppConstants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    private var userRepository = UserRepository()

    private val _registerInfo: MutableLiveData<AppConstants.Companion.RegisterErrors> =
        MutableLiveData()
    val registerInfo: LiveData<AppConstants.Companion.RegisterErrors> get() = _registerInfo


    fun registerUser(user: UserEntity) {
        with(Firebase.auth) {
            createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Pedro", "createUserWithEmailAndPassword:success")
                        this.uid?.let { userRepository.createUser(user, it) }
                        _registerInfo.postValue(AppConstants.Companion.RegisterErrors.SUCCESS)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Pedro", "createUserWithEmailAndPassword:failure", task.exception)
                        _registerInfo.postValue(AppConstants.Companion.RegisterErrors.ALREADY_EXIST)
                    }
                }
        }

    }
}