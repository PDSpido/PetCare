package com.example.petcare.data.repository

import com.example.petcare.data.entity.PostEntity
import com.example.petcare.util.FirebaseConstants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase

class PostRepository {

    fun registerPost(post: PostEntity) {
        with(
            FirebaseDatabase.getInstance().reference.child(FirebaseConstants.POST_REPO).push()
        ) {
            child(FirebaseConstants.POST_POST_TYPE).setValue(post.postType)
            child(FirebaseConstants.POST_USER_ID).setValue(post.userId)
            child(FirebaseConstants.POST_USER_TYPE).setValue(post.userType)
            child(FirebaseConstants.POST_VALUE_DESIRED).setValue(post.valueDesired)
            child(FirebaseConstants.POST_VALUE_DONATED).setValue(post.valueDonated)
            child(FirebaseConstants.POST_TiTLE).setValue(post.title)
            child(FirebaseConstants.POST_DESCRIPTION).setValue(post.description)
            child(FirebaseConstants.POST_PICTURE).setValue(post.picture)
        }
    }

    fun getAllPosts(valueEventListener: ChildEventListener) {
        FirebaseDatabase.getInstance().reference.child(FirebaseConstants.POST_REPO)
            .addChildEventListener(valueEventListener)
    }

    fun addContribution(postUid: String?, newValue: Float) {
        with(postUid?.let { FirebaseDatabase.getInstance().reference.child(FirebaseConstants.POST_REPO)
            .child(it).child(FirebaseConstants.POST_VALUE_DONATED) }) {
            this?.setValue(newValue)
        }

    }

}