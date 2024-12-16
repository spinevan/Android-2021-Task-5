package com.example.android2021task5.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.databinding.CatItemViewBinding
import com.example.android2021task5.interfaces.ICatImageListener
import com.example.android2021task5.ui.viewHolders.CatImageViewHolder

class CatImageAdapter(private val listener: ICatImageListener) :
    ListAdapter<CatImage, CatImageViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemViewBinding.inflate(layoutInflater, parent, false)
        return CatImageViewHolder(binding, listener, binding.root.context.resources)
    }

    override fun onBindViewHolder(holder: CatImageViewHolder, position: Int) {
        val current: CatImage = getItem(position)
        holder.bind(current)

        if ((position >= itemCount - 1)) {
            listener.loadNextPage()
        }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<CatImage>() {

            override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
                return oldItem.id == newItem.id &&
                    oldItem.url == newItem.url
            }

            override fun getChangePayload(oldItem: CatImage, newItem: CatImage) = Any()
        }
    }
}
