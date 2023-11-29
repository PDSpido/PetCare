package com.example.petcare.ui.donation

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

class DonationViewModel : ViewModel() {
    private var postRepository = PostRepository()

    private val _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData

    fun getAllPosts() {
        val list: MutableList<PostEntity> = mutableListOf()
        postRepository.getAllPosts(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    if (isDonationType(snapshot)) {
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

    fun addContribution(postUid: String?, newValue: Float) {
        Log.i("pedro", "postId: $postUid, newValue: $newValue")
        postRepository.addContribution(postUid, newValue)

    }

    private fun isDonationType(snapshot: DataSnapshot): Boolean =
        snapshot.child(FirebaseConstants.POST_POST_TYPE)
            .getValue<Long>() == AppConstants.POST_TYPE_DONATION.toLong()

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
}