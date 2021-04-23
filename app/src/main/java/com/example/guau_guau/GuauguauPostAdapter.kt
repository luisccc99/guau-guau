package com.example.guau_guau

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.databinding.PostItemBinding

class GuauguauPostAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<GuauguauPost, GuauguauPostAdapter.PostViewHolder>(
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

    inner class PostViewHolder (private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(post: GuauguauPost) {
            ViewCompat.setTransitionName(binding.imageViewPostPic, post.id)
            binding.apply {
                Glide.with(itemView)
                    .load(post.photo.url)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_person)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewUserPic)

                Glide.with(itemView)
                    .load(post.photo.url)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_article)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewPostPic)

                    textViewPostTitle.text = post.title
                    textViewPostDescription.text = post.body
                    textViewUsername.text = post.user_id

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(post: GuauguauPost);
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