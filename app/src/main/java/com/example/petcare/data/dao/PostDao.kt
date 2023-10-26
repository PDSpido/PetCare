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
}