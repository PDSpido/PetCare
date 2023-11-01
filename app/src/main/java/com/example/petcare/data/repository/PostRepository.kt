package com.example.petcare.data.repository

import android.content.Context
import com.example.petcare.data.PetCareDatabase
import com.example.petcare.data.entity.PostEntity
import kotlinx.coroutines.flow.Flow

class PostRepository(context: Context){
    private val postDao = PetCareDatabase.getInstance(context).postDao()

    fun createPost(post: PostEntity) = postDao.insert(post = post)

    fun addContribution(postId: Int, newValue: Float) = postDao.addContribution(postId, newValue)

    fun flowAll(): Flow<List<PostEntity>> = postDao.flowAll()

    fun flowAllDonationPosts(): Flow<List<PostEntity>> = postDao.flowAllDonationPosts()
    fun flowAllFeedPosts(): Flow<List<PostEntity>> = postDao.flowAllFeedPosts()

}