package com.example.android2021task5.ui.viewHolders

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android2021task5.R
import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.databinding.CatItemViewBinding
import com.example.android2021task5.interfaces.ICatImageListener

class CatImageViewHolder(
    private val binding: CatItemViewBinding,
    private val listener: ICatImageListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(catImage: CatImage) {
        Glide.with(binding.root)
            .load(catImage.url)
            .centerCrop()
            .placeholder(R.drawable.ic_progress)
            .into(binding.catImage)

        binding.catImage.setOnClickListener {
            listener.openImageFragment(catImage.url!!)
        }
    }
}
