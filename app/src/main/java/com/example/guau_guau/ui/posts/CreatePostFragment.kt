package com.example.guau_guau.ui.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.databinding.FragmentCreatePostBinding
import com.example.guau_guau.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class CreatePostFragment :
    BaseFragment<PostViewModel, FragmentCreatePostBinding, PostRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }

        binding.buttonSubmit.setOnClickListener {
        if (userId != null) {
            val title = binding.editTextTitle.text.toString()
            val body = binding.editTextDescription.text.toString()
            viewModel.postSubmit(userId, title, body)
                }        
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