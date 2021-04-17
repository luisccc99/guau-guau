package com.example.guau_guau.ui.posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.guau_guau.GuauguauPostAdapter
import com.example.guau_guau.R
import com.example.guau_guau.data.GuauguauPost
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.databinding.FragmentPostsBinding
import com.example.guau_guau.databinding.PostItemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class PostsFragment : Fragment(), GuauguauPostAdapter.OnItemClickListener{

    private val viewModel by viewModels<PostGETViewModel>()

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsBinding.bind(view)
        val adapter = GuauguauPostAdapter(this)
        binding.apply {
            recyclerViewPosts.setHasFixedSize(true)
            recyclerViewPosts.adapter = adapter
        }
        viewModel.posts.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        view.findViewById<FloatingActionButton>(R.id.floating_create_post).setOnClickListener {
            view.findNavController().navigate(R.id.action_postsFragment_to_createPostFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(post: GuauguauPost) {
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(post)
        findNavController().navigate(action)
    }

}