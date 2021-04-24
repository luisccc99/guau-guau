package com.example.guau_guau.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.guau_guau.GuauguauCommentAdapter
import com.example.guau_guau.R
import com.example.guau_guau.databinding.CommentItemBinding
import com.example.guau_guau.databinding.FragmentCommentsListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CommentFragment : Fragment(R.layout.fragment_comments_list){

    private val viewModel by viewModels<CommentsViewModel>()

    private var _binding: FragmentCommentsListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCommentsListBinding.bind(view)

        val adapter = GuauguauCommentAdapter()

        binding.apply {
            recyclerViewComments.setHasFixedSize(true)
            recyclerViewComments.adapter = adapter
        }

        viewModel.comments.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}