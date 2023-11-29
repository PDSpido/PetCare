package com.example.petcare.data.entity

data class PostEntity(
    val postUid: String?,
    val postType: Long?,
    val userId: String?,
    val userType: Long?,
    val valueDesired: Float?,
    val valueDonated: Float?,
    val title: String?,
    val description: String?,
    val picture: String?,
)