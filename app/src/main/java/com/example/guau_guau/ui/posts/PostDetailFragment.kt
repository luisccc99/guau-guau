package com.example.guau_guau.ui.posts

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.guau_guau.R
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.databinding.FragmentPostDetailBinding
import com.example.guau_guau.ui.Funs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PostDetailFragment : Fragment() {

    private val args by navArgs<PostDetailFragmentArgs>()
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonComments.setOnClickListener {
            view.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment)
        }
        val userId = runBlocking { UserPreferences(requireContext()).userId.first() }
        if (userId != args.post.user_id) {
            binding.imageViewSolve.visibility = View.GONE
        }
        binding.imageViewSolve.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.solve_post -> {
                        Toast.makeText(
                            requireContext(),
                            "Solve Post",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    R.id.delete_post -> {
                        Toast.makeText(requireContext(), "Delete Post", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }
        binding.apply {
            val post = args.post
            Glide.with(this@PostDetailFragment)
                .load(post.user_photo)
                .error(R.drawable.ic_baseline_article)
                .into(imageViewPostPic)

            Glide.with(this@PostDetailFragment)
                .load(post.publi_photo.url)
                .error(R.drawable.ic_baseline_person)
                .into(imageViewProfilePic)

            textViewUsername.text = "${post.name} ${post.lastname} • ${
                Funs.getStringDateFormatFrom(
                    post.created_at
                )
            }"
            textViewPostTitle.text = post.title
            textViewPostDescription.text = post.body

        }
    }
}