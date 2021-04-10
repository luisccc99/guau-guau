package com.example.guau_guau.ui.posts

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.databinding.FragmentCreatePostBinding
import com.example.guau_guau.databinding.FragmentPostsBinding
import com.example.guau_guau.databinding.FragmentSignupBinding
import com.example.guau_guau.ui.auth.SignUpViewModel
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.ui.enable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CreatePostFragment : BaseFragment<PostViewModel, FragmentCreatePostBinding, PostRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }

        // TODO: make userId non nullable
        binding.buttonSubmit.setOnClickListener {
        if (userId != null) {
            val title = binding.editTextTitle.text.toString()
            val body = binding.editTextDescription.text.toString()
            viewModel.postSubmit(userId, title, body)
                }
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