package com.example.petcare.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.databinding.ItemLayoutPostBinding

class FeedAdapter(
    private val renderList: List<PostEntity>,
    private val onClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<FeedAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ItemLayoutPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = renderList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(renderList[position])

    inner class PostViewHolder(private val binding: ItemLayoutPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            with(binding) {
                feedItemTittle.text = post.tittle
                feedItemDescription.text = post.description
                Glide.with(binding.root).load(post.picture).into(binding.feedItemPicture)

                feedItemReport.setOnClickListener(onClickListener)
            }
        }
    }
}