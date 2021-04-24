package com.example.guau_guau.ui.posts

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.guau_guau.R
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.databinding.FragmentPostDetailBinding
import com.google.android.gms.common.util.Strings

class PostDetailFragment : Fragment() {

    private val args by navArgs<PostDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  binding = FragmentPostDetailBinding.bind(view)
        binding.buttonComments.setOnClickListener {
            view.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment)
        }
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