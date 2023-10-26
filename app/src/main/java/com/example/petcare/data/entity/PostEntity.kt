package com.example.petcare.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.petcare.util.AppConstants

@Entity(tableName = AppConstants.POST_TABLE)
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "postType") val postType: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "valueDesired") val valueDesired: Float,
    @ColumnInfo(name = "valueDonated") val valueDonated: Float,
    @ColumnInfo(name = "tittle") val tittle: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "picture") val picture: String,
)