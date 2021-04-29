package com.example.guau_guau.ui.posts

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.guau_guau.R
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.databinding.FragmentPostDetailBinding
import com.example.guau_guau.ui.Funs
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.ui.handleApiError
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PostDetailFragment :
    BaseFragment<PostViewModel, FragmentPostDetailBinding, PostRepository>() {

    private val args by navArgs<PostDetailFragmentArgs>()
    private val solveReasons =
        arrayOf("I haven\'t seen the dog in days", "I adopted the dog", "Other")
    private var checkedItem = 0
    private lateinit var postCreatorId: String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonComments.setOnClickListener {
            view.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment)
        }
        binding.imageViewSolve.visibility = View.GONE

        val userId = runBlocking { UserPreferences(requireContext()).userId.first() }

        showPostInfo(args.post)
        viewModel.getPost(args.post.id)
        viewModel.post.observe(viewLifecycleOwner, {

            when (it) {
                is Resource.Success -> {
                    Log.wtf("SUCCESS", "onViewCreated: ${it.value.user_id}")
                    // if user deleted the post, navigate to home
                    if (it.value.message != null) {
                        Toast.makeText(
                            requireContext(),
                            "Post has been deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToHome(view)
                    }

                    // if user solved a post, navigate to home
                    if (it.value.resolved) {
                        Toast.makeText(
                            requireContext(),
                            "Post solved sucessfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToHome(view)
                    }
                    // if current user created the post, display pop menu
                    if (userId != null && userId == it.value.user_id) {
                        setupSolveAndDeleteOptions()
                    }
                }
                is Resource.Failure -> handleApiError(it) {

                }
                is Resource.Loading -> {

                }

            }
        })
    }

    private fun navigateToHome(view: View) {
        view.findNavController().navigate(R.id.action_postDetailFragment_to_postsFragment)
    }


    private fun setupSolveAndDeleteOptions() {
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
                                // call to solve post with a solve reason
                                viewModel.solvePost(
                                    args.post.id,
                                    postCreatorId,
                                    true,
                                    solveReasons[checkedItem]
                                )
                            }
                            .setSingleChoiceItems(solveReasons, checkedItem) { _, which ->
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
                                // call to delete a post
                                viewModel.deletePost(args.post.id, postCreatorId)
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
    }

    @SuppressLint("SetTextI18n")
    private fun showPostInfo(post: GuauguauPost) {
        binding.apply {
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

    override fun getViewModel() = PostViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostDetailBinding {
        return FragmentPostDetailBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): PostRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(GuauguauApi::class.java, token)
        return PostRepository(api)
    }
}