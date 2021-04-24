package com.example.guau_guau

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.guau_guau.data.GuauguauComment
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.databinding.CommentItemBinding
import org.w3c.dom.Comment

class GuauguauCommentAdapter : PagingDataAdapter<GuauguauComment, GuauguauCommentAdapter.CommentViewHolder>(
    COMMENT_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = getItem(position)


        if (currentItem != null){
            holder.bind(currentItem)
        }
    }


    class CommentViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(comment: GuauguauComment){

                binding.apply {
                    txtBody.text = comment.body
                    txtDate.text = comment.created_at
                   // txtUserName.text = comment.id
                  //  txtUserLastname.text = comment.id
                }
            }
        }


    companion object{
        private val COMMENT_COMPARATOR = object : DiffUtil.ItemCallback<GuauguauComment>() {
            override fun areItemsTheSame(
                oldItem: GuauguauComment,
                newItem: GuauguauComment
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GuauguauComment,
                newItem: GuauguauComment
            ) = oldItem == newItem
        }
    }

}