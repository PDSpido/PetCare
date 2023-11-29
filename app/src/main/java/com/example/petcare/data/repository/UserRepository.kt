package com.example.petcare.data.repository

import android.util.Log
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.util.FirebaseConstants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase

class UserRepository {

    fun getUserType(uid: String?): Long? {
        var result: Long? = 0L
        uid?.let {
            FirebaseDatabase.getInstance().reference.child(FirebaseConstants.USER_REPO).child(it)
                .child(FirebaseConstants.USER_USERTYPE).addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            result = snapshot.getValue<Long>()
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    }
                )
        }
        return result
    }

    fun tryLogin(email: String, password: String): Boolean {
        var result = false
        with(Firebase.auth) {
            signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    result = if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Pedro", "signInWithEmail:success")
                        true
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Pedro", "signInWithEmail:failure", task.exception)
                        false
                    }
                }
        }
        return result
    }

    fun createUser(user: UserEntity, uid: String) {
        with(
            FirebaseDatabase.getInstance().reference.child(FirebaseConstants.USER_REPO).child(uid)
        ) {
            child(FirebaseConstants.USER_EMAIL).setValue(user.email)
            child(FirebaseConstants.USER_PASSWORD).setValue(user.password)
            child(FirebaseConstants.USER_USERNAME).setValue(user.username)
            child(FirebaseConstants.USER_USERTYPE).setValue(user.userType)
            child(FirebaseConstants.USER_PHONE_NUMBER).setValue(user.contact)
            child(FirebaseConstants.USER_DOCUMENT).setValue(user.document)
        }
    }
}