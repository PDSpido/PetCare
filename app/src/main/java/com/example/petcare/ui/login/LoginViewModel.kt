package com.example.petcare.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginInfo: MutableLiveData<Boolean> = MutableLiveData()
    val loginInfo: LiveData<Boolean> get() = _loginInfo

    fun tryLogin(email: String, password: String) {
        viewModelScope.launch {
            with(Firebase.auth) {
                signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        _loginInfo.postValue(if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Pedro", "signInWithEmail:success")
                            true
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Pedro", "signInWithEmail:failure", task.exception)
                            false
                        })
                    }
            }
        }
    }
}