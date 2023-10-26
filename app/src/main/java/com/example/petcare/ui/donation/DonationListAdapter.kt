package com.example.petcare.ui.donation

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petcare.data.entity.PostEntity
import com.example.petcare.databinding.ItemLayoutDonationBinding

class DonationListAdapter(
    private val context: Context,
    private val renderList: List<PostEntity>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<DonationListAdapter.DonationItemViewHolder>() {

    inner class DonationItemViewHolder(private val binding: ItemLayoutDonationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostEntity) {
            with(binding) {
                donationItemTittle.text = item.tittle
                donationItemDescription.text = item.description
                donationItemValueDonated.text = "Doado: ${item.valueDonated}"
                donationItemValueDesired.text = "Desejado: ${item.valueDesired}"
                Glide.with(binding.root).load(item.picture).into(binding.donationItemPicture)
                donationItemButton.tag = item.uid
                donationItemButton.setOnClickListener(onClickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationItemViewHolder =
        DonationItemViewHolder(
            ItemLayoutDonationBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = renderList.size

    override fun onBindViewHolder(holder: DonationItemViewHolder, position: Int) =
        holder.bind(renderList[position])
}