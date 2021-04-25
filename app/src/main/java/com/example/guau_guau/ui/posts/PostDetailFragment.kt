package com.example.guau_guau.ui.posts

import android.annotation.SuppressLint
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
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.databinding.FragmentPostDetailBinding
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.ui.profile.ProfileViewModel
import com.google.android.gms.common.util.Strings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PostDetailFragment :
    BaseFragment<ProfileViewModel, FragmentPostDetailBinding, UserRepository>() {

    private val args by navArgs<PostDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostDetailBinding.bind(view)
        binding.buttonComments.setOnClickListener {
            view.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment)
        }

        viewModel.getUser(args.post.user_id)
        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val user = it.value
                    with(binding) {
                        textViewUsername.text = "${user.name.capitalize()} ${user.lastname.capitalize()}"
                        Glide.with(this@PostDetailFragment)
                            .load(user.photo.url)
                            .dontAnimate()
                            .error(R.drawable.ic_baseline_person)
                            .placeholder(R.drawable.ic_baseline_person)
                            .into(imageViewProfilePic)
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.apply {
            val post = args.post
            Glide.with(this@PostDetailFragment)
                .load(post.photo.url)
                .error(R.drawable.ic_baseline_article)
                .into(imageViewPostPic)
            textViewPostTitle.text = post.title
            textViewPostDescription.text = post.body

        }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPostDetailBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(GuauguauApi::class.java, token)
        return UserRepository(api)
    }
}