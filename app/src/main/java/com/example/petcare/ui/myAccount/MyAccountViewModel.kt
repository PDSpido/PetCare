package com.example.petcare.ui.myAccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.data.repository.PostRepository
import com.example.petcare.data.repository.UserRepository
import com.example.petcare.util.FirebaseConstants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MyAccountViewModel : ViewModel() {
    private var postRepository = PostRepository()

    private val _postListData: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val postListData: LiveData<List<PostEntity>> get() = _postListData

    private val _userData: MutableLiveData<UserEntity> = MutableLiveData()
    val userData: LiveData<UserEntity> get() = _userData

    fun getAllPostsByUser(uid: String) {
        val list: MutableList<PostEntity> = mutableListOf()
        postRepository.getAllPosts(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                if (_postListData.isInitialized) list.addAll(_postListData.value!!)
                if (snapshot.exists()) {
                    if (isMadeByUser(uid, snapshot)) {
                        list.add(createPostEntity(snapshot))
                    }
                }
                _postListData.postValue(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun isMadeByUser(uid: String, snapshot: DataSnapshot): Boolean =
        snapshot.child(FirebaseConstants.POST_USER_ID).getValue<String>() == uid


    fun getUserById(uid: String) {
        Log.i("pedro", "getUserById: ${uid}")

        FirebaseDatabase.getInstance().reference.child(FirebaseConstants.USER_REPO).child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("pedro", "onDataChange")
                    with(snapshot) {
                        _userData.postValue(UserEntity(
                            email = child(FirebaseConstants.USER_EMAIL).getValue<String>(),
                            password = child(FirebaseConstants.USER_PASSWORD).getValue<String>(),
                            username = child(FirebaseConstants.USER_USERNAME).getValue<String>(),
                            userType = child(FirebaseConstants.USER_USERTYPE).getValue<Long>(),
                            contact = child(FirebaseConstants.USER_PHONE_NUMBER).getValue<String>(),
                            document = child(FirebaseConstants.USER_DOCUMENT).getValue<String>()
                        ))
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            }
            )
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
}