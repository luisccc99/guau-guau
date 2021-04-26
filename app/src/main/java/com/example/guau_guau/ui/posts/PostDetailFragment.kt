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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PostDetailFragment : Fragment() {

    private val args by navArgs<PostDetailFragmentArgs>()
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private val solveReasons = arrayOf("I haven't seen the dog in days", "I adopted the dog", "Other")
    private var checkedItem = 0

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
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.solve_post))
                            .setNeutralButton(getString(R.string.cancel), null)
                            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                                //TODO: make call to solve post (patch)
                                Toast.makeText(requireContext(), "Solving post because ${solveReasons[checkedItem]}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .setSingleChoiceItems(solveReasons, checkedItem){ _, which ->
                                checkedItem = which
                            }
                            .show()
                        true
                    }
                    R.id.delete_post -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(getString(R.string.delete_post))
                            .setMessage(getString(R.string.delete_confirmation))
                            .setNegativeButton(getString(R.string.cancel), null)
                            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                                //TODO: make call to delete post
                                Toast.makeText(
                                    requireContext(),
                                    "deleting post...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .show()
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
                .load("https://res-4.cloudinary.com/wofwof/${post.user_photo}")
                .error(R.drawable.ic_baseline_person)
                .into(imageViewProfilePic)

            Glide.with(this@PostDetailFragment)
                .load(post.publi_photo.url)
                .error(R.drawable.ic_baseline_article)
                .into(imageViewPostPic)

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