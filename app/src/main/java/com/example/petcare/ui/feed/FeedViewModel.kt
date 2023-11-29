package com.example.petcare.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.util.AppConstants
import com.example.petcare.util.FirebaseConstants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue

class FeedViewModel : ViewModel() {

    private var postRepository = PostRepository()

    private var _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData

    fun getAllPosts(type: Long) {
        val list: MutableList<PostEntity> = mutableListOf()
        postRepository.getAllPosts(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (isType(type, snapshot)) {
                        list.add(createPostEntity(snapshot))
                    }
                }
                Log.i("pedro", "list: $list")
                _postListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })

    }

    fun getAllPostsFromText(text: CharSequence?) {
        _postListData.postValue(_postListData.value?.filter { containsText(text, it) })
    }

    private fun createPostEntity(snapshot: DataSnapshot): PostEntity {
        return with(snapshot) {
            PostEntity(
                postUid = key,
                postType = child(FirebaseConstants.POST_POST_TYPE).getValue<Long>(),
                userId = child(FirebaseConstants.POST_USER_ID).getValue<String>(),
                userType = child(FirebaseConstants.POST_USER_TYPE).getValue<Long>(),
                valueDesired = child(FirebaseConstants.POST_VALUE_DESIRED).getValue<Float>(),
                valueDonated = child(FirebaseConstants.POST_VALUE_DONATED).getValue<Float>(),
                title = child(FirebaseConstants.POST_TiTLE).getValue<String>(),
                description = child(FirebaseConstants.POST_DESCRIPTION).getValue<String>(),
                picture = child(FirebaseConstants.POST_PICTURE).getValue<String>()
            )
        }
    }

    private fun isType(type: Long, snapshot: DataSnapshot): Boolean =
        snapshot.child(FirebaseConstants.POST_POST_TYPE).getValue<Long>() == AppConstants.POST_TYPE_FEED.toLong() &&
                snapshot.child(FirebaseConstants.USER_USERTYPE).getValue<Long>() == type

    private fun containsText(text: CharSequence?, postEntity: PostEntity): Boolean =
        (text?.let { postEntity.description?.contains(it) } == true || text?.let {
            postEntity.title?.contains(it) } == true)
}