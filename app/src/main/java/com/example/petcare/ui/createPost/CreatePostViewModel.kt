package com.example.petcare.ui.createPost

import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePostViewModel : ViewModel() {
    private  var postRepository = PostRepository()

    fun createPost(post: PostEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            postRepository.registerPost(post)
        }
    }
}