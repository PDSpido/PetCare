package com.example.petcare.ui.donation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.util.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DonationViewModel : ViewModel() {
    private lateinit var postRepository: PostRepository
    private lateinit var sharedPreferences: SharedPreferences

    private val _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData

    fun initRepositories(context: Context) {
        postRepository = PostRepository(context)
        sharedPreferences =
            context.getSharedPreferences(AppConstants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getAllPosts() {
        viewModelScope.launch {
            postRepository.flowAllDonationPosts().onEach {
                _postListData.postValue(it)
            }.collect()
        }
    }

    fun addContribution(postId: Int, newValue: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("pedro", "postId: $postId, newValue: $newValue")
            postRepository.addContribution(postId, newValue)
        }
    }
}