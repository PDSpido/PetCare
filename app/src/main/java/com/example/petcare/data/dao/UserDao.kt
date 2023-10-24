package com.example.petcare.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM ${AppConstants.USER_TABLE}")
    fun flowAll(): Flow<List<UserEntity>>

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM ${AppConstants.USER_TABLE} WHERE email = :email")
    fun findUserByEmail(email: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)
}