package com.example.petcare.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.petcare.util.AppConstants

@Entity(tableName = AppConstants.USER_TABLE)
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "userType") val userType: Int,
    @ColumnInfo(name = "phoneNumber") val contact: String?,
    @ColumnInfo(name = "document") val document: String?
)
