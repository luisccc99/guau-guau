package com.example.guau_guau.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.guau_guau.R
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {

    private val args by navArgs<PostDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  binding = FragmentPostDetailBinding.bind(view)
        binding.apply {
           val post = args.post
            Glide.with(this@PostDetailFragment)
                .load(post.photo.url)
                .error(R.drawable.ic_baseline_article)
                .into(imageViewPostPic)
            textViewUsername.text = post.user_id
            textViewPostTitle.text = post.title
            textViewPostDescription.text = post.body

        }
    }
}