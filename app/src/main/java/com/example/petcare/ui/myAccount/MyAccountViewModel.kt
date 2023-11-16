package com.example.petcare.ui.myAccount

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.data.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyAccountViewModel : ViewModel() {
    private lateinit var postRepository: PostRepository
    private lateinit var userRepository: UserRepository

    private val _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData


    private val _userData: MutableLiveData<UserEntity> = MutableLiveData()
    val userData: LiveData<UserEntity> get() = _userData

    fun initRepositories(context: Context) {
        postRepository = PostRepository(context)
        userRepository = UserRepository(context)
    }

    fun getAllPostsByUser(id: Int) {
        viewModelScope.launch {
            postRepository.flowPostsById(id).onEach {
                _postListData.postValue(it)
            }.collect()
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            userRepository.flowUserById(id).onEach {
                _userData.postValue(it)
            }.collect()
        }
    }
}