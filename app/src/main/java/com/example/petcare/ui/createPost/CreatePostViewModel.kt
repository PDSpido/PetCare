package com.example.petcare.ui.createPost

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePostViewModel : ViewModel() {
    private lateinit var postRepository: PostRepository
    private lateinit var sharedPreferences: SharedPreferences

    fun initRepositories(context: Context) {
        postRepository = PostRepository(context)
        sharedPreferences =
            context.getSharedPreferences(AppConstants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun createPost(post: PostEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            postRepository.createPost(post)
        }
    }
}