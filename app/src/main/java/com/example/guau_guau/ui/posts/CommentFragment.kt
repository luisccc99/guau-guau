package com.example.guau_guau.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.guau_guau.GuauguauCommentAdapter
import com.example.guau_guau.R
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.databinding.CommentItemBinding
import com.example.guau_guau.databinding.FragmentCommentsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class CommentFragment : Fragment(R.layout.fragment_comments_list){

    private val viewModel by viewModels<CommentsViewModel>()
    private val args: CommentFragmentArgs by navArgs()
    private var _binding: FragmentCommentsListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCommentsListBinding.bind(view)
        val userId = runBlocking {  UserPreferences(requireContext()).userId.first() }
        val token = runBlocking {  UserPreferences(requireContext()).authToken.first() }
        val adapter = GuauguauCommentAdapter()
        getComments()
        binding.apply {
            recyclerViewComments.setHasFixedSize(true)
            recyclerViewComments.adapter = adapter
        }

        viewModel.comments.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.submitComment.setOnClickListener {
            val body = binding.editTextDescription.text.toString().trim()
            if (body.isNotEmpty()) {
                if (userId != null && token != null) {
                    binding.editTextDescription.text?.clear()
                    viewModel.createComment(userId, args.postId, body, token)
                    getComments()
                }
            }
        }
    }

    private fun getComments() {
        binding.recyclerViewComments.scrollToPosition(0)
        viewModel.searchComments(args.postId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}