package com.example.petcare.data.repository

import android.content.Context
import com.example.petcare.data.PetCareDatabase
import com.example.petcare.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(context: Context){
    private val userDao = PetCareDatabase.getInstance(context).userDao()

    fun addUser(user: UserEntity) = userDao.insert(user = user)

    fun flowUserByEmail(email: String): Flow<UserEntity?> = userDao.findUserByEmail(email)
}