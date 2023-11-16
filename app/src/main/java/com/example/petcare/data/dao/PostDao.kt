package com.example.petcare.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM ${AppConstants.POST_TABLE}")
    fun flowAll(): Flow<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE ${AppConstants.POST_TABLE} SET valueDonated = :newValue + valueDonated WHERE postType = :postId")
    fun addContribution(postId: Int, newValue: Float)

    @Query("SELECT * FROM ${AppConstants.POST_TABLE} WHERE postType = ${AppConstants.POST_TYPE_DONATION}")
    fun flowAllDonationPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM ${AppConstants.POST_TABLE} WHERE postType = ${AppConstants.POST_TYPE_FEED}")
    fun flowAllFeedPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM ${AppConstants.POST_TABLE} WHERE postType = ${AppConstants.POST_TYPE_FEED} AND tittle LiKE :text OR description LIKE :text")
    fun flowAllFeedPostsByText(text: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM ${AppConstants.POST_TABLE} WHERE postType = ${AppConstants.POST_TYPE_FEED} AND userId = :id")
    fun flowPostsById(id: Int): Flow<List<PostEntity>>
}