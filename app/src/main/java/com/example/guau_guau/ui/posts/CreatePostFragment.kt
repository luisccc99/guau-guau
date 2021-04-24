package com.example.guau_guau.ui.posts

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.databinding.FragmentCreatePostBinding
import com.example.guau_guau.ui.base.BaseFragment
import com.google.android.gms.common.util.Strings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class CreatePostFragment :
    BaseFragment<PostViewModel, FragmentCreatePostBinding, PostRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.slide_bottom
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }

        binding.buttonSubmit.setOnClickListener {
            if (userId != null) {
                val title = binding.editTextTitle.text.toString()
                val body = binding.editTextDescription.text.toString()
                if (!Strings.isEmptyOrWhitespace(title) && !Strings.isEmptyOrWhitespace(body)){
                    viewModel.postSubmit(userId, title, body)
                }
                Toast.makeText(requireContext(), "Invalid data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelPost.setOnClickListener {
            view.findNavController().navigate(R.id.action_createPostFragment_to_postsFragment)
        }

        binding.goToPhoto.setOnClickListener {
            view.findNavController().navigate(R.id.action_createPostFragment_to_photoSelecTaker)
        }

    }

    override fun getViewModel() = PostViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreatePostBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        PostRepository(remoteDataSource.buildApi(GuauguauApi::class.java))
}