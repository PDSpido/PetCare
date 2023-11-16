package com.example.petcare.ui.feed

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private lateinit var postRepository: PostRepository
    private lateinit var sharedPreferences: SharedPreferences

    private val _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData

    fun initRepositories(context: Context) {
        postRepository = PostRepository(context)
        sharedPreferences =
            context.getSharedPreferences(AppConstants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getAllPosts(type: Int) {
        viewModelScope.launch {
            postRepository.flowAllFeedPosts().onEach { list ->
                _postListData.postValue(list.filter { it.userType == type})
            }.collect()


        }
    }

    fun getAllPostsFromText(type: Int, text: String) {
        viewModelScope.launch {
            postRepository.flowAllFeedPostsByText(text).onEach { list ->
                _postListData.postValue(list.filter { it.userType == type})
            }.collect()
        }
    }
}