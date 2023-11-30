package com.example.petcare.data.repository

import com.example.petcare.data.entity.UserEntity
import com.example.petcare.util.FirebaseConstants
import com.google.firebase.database.FirebaseDatabase

class UserRepository {

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