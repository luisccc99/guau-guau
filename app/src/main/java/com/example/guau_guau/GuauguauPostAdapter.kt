package com.example.guau_guau

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.databinding.PostItemBinding

class GuauguauPostAdapter : PagingDataAdapter<GuauguauPost, GuauguauPostAdapter.PostViewHolder>(
    POST_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    class  PostViewHolder (private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: GuauguauPost) {
            binding.apply {
                Glide.with(itemView)
                    .load(post.photo.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewPhoto)
                Glide.with(itemView)
                    .load(post.user_photo.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                    textViewPostTitle.text = post.title
                    textViewPostDescription.text = post.body
                    textViewUsername.text = post.user_id

            }
        }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<GuauguauPost>() {
            override fun areItemsTheSame(oldItem: GuauguauPost, newItem: GuauguauPost) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GuauguauPost, newItem: GuauguauPost) =
                oldItem == newItem

        }
    }
}